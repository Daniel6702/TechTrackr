package com.example.techtrackr.data.repository

import android.util.Log
import kotlinx.coroutines.delay

suspend fun <T> retryOperation(
    times: Int = 3,
    initialDelay: Long = 1500,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: Exception) {
            Log.e("RetryOperation", "Retry attempt failed: ${e.message}")
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong()
    }
    // Last attempt
    return block()
}
