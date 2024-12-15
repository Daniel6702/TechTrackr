package com.example.techtrackr

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.techtrackr.workers.PriceMonitorWorker
import java.util.concurrent.TimeUnit

class TechtrackrApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        enqueuePriceMonitorWorker()
    }

    private fun enqueuePriceMonitorWorker() {
        val oneTimeRequest = OneTimeWorkRequestBuilder<PriceMonitorWorker>()
            .setInitialDelay(15, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(this).enqueue(oneTimeRequest)
    }
}
