package com.example.compose.benchmark

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.channels.Channel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChannelBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private val conflated = Channel<Unit>(Channel.CONFLATED)
    private val normal = Channel<Unit>(capacity = 1)

    @Test
    fun conflatedChannelSend() {
        // send first thing to measure sending to already full channel
        conflated.trySend(Unit)

        benchmarkRule.measureRepeated {
            conflated.trySend(Unit)
        }
    }

    @Test
    fun conflatedChannelSend_empty() {
        benchmarkRule.measureRepeated {
            conflated.trySend(Unit)

            runWithTimingDisabled {
                conflated.tryReceive()
            }
        }
    }

    @Test
    fun normalChannelSend() {
        // send first thing to measure sending to already full channel
        normal.trySend(Unit)

        benchmarkRule.measureRepeated {
            normal.trySend(Unit)
        }
    }

    @Test
    fun normalChannelSend_empty() {
        benchmarkRule.measureRepeated {
            normal.trySend(Unit)

            runWithTimingDisabled {
                normal.tryReceive()
            }
        }
    }
}