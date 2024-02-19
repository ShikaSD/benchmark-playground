@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.androidx.benchmark)
    alias(libs.plugins.kotlinAndroid)
}
apply(plugin = "kotlinx-atomicfu")

android {
    namespace = "com.example.compose.benchmark"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    defaultConfig {
        minSdk = 24
        buildToolsVersion = "34.0.0"

        testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"
    }
    buildFeatures {
        compose = true
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.8"
        }
    }

    testBuildType = "release"
    buildTypes {
        debug {
            // Since isDebuggable can"t be modified by gradle for library modules,
            // it must be done in a manifest - see src/androidTest/AndroidManifest.xml
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "benchmark-proguard-rules.pro"
            )
        }
        release {
            isDefault = true
        }
    }

    defaultConfig {
        // must be one of: 'None', 'StackSampling', or 'MethodTracing'
//        testInstrumentationRunnerArguments["androidx.benchmark.profiling.mode"]= "MethodTracing"
    }

    // Precompile APK
    adbOptions.setInstallOptions(
        "/data/local/tmp/${project.name}-$testBuildType-androidTest.apk && cmd package compile -m speed -f $namespace.test"
    )
}

dependencies {
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.benchmark.junit4)
    // Add your dependencies here. Note that you cannot benchmark code
    // in an app module this way - you will need to move any code you
    // want to benchmark to a library module:
    // https://developer.android.com/studio/projects/android-library#Convert
    androidTestImplementation(libs.core.ktx)
    androidTestImplementation(libs.appcompat)
    androidTestImplementation(libs.material)
    androidTestImplementation(libs.ui)
    androidTestImplementation(libs.ui.graphics)
    androidTestImplementation(libs.ui.tooling.preview)
    androidTestImplementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.activity)
    androidTestImplementation(libs.atomicfu)
}