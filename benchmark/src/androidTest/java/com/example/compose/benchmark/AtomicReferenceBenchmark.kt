package com.example.compose.benchmark

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.atomicfu.atomic
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicReference

@RunWith(AndroidJUnit4::class)
class AtomicReferenceBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()
    
    private val atomicReference = AtomicReference(false)
    private val atomicRef = atomic(false)
    
    @Test
    fun atomicReference_compareAndSet() {
        benchmarkRule.measureRepeated { 
            atomicReference.compareAndSet(true, false)
            atomicReference.compareAndSet(false, true)
        }
    }

    @Test
    fun atomicReference_get() {
        benchmarkRule.measureRepeated {
            atomicReference.get()
        }
    }

    @Test
    fun atomicReference_getAndSwap() {
        benchmarkRule.measureRepeated {
            val a = atomicReference.get()
            atomicReference.compareAndSet(a, !a)
        }
    }

    @Test
    fun atomicReference_lazySet() {
        benchmarkRule.measureRepeated {
            atomicReference.lazySet(true)
            atomicReference.lazySet(false)
        }
    }

    @Test
    fun atomicRef_compareAndSet() {
        benchmarkRule.measureRepeated {
            atomicRef.compareAndSet(true, false)
            atomicRef.compareAndSet(false, true)
        }
    }

    @Test
    fun atomicRef_get() {
        benchmarkRule.measureRepeated {
            atomicRef.value
        }
    }

    @Test
    fun atomicRef_getAndSwap() {
        benchmarkRule.measureRepeated {
            val a = atomicRef.value
            atomicRef.compareAndSet(a, !a)
        }
    }

    @Test
    fun atomicRef_lazySet() {
        benchmarkRule.measureRepeated {
            atomicRef.lazySet(true)
            atomicRef.lazySet(false)
        }
    }
}