package com.example.techtrackr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.techtrackr.data.shared.LocalSharedDataViewModel
import com.example.techtrackr.data.shared.SharedDataViewModel
import com.example.techtrackr.ui.navigation.AppNavHost
import com.example.techtrackr.ui.navigation.LocalNavController
import com.example.techtrackr.ui.theme.TechtrackrTheme
import com.example.techtrackr.utils.SharedPreferencesUtils
import com.example.techtrackr.workers.PriceMonitorWorker
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesUtils.init(applicationContext)
        setupPriceMonitorWorker()

        //val oneTimeRequest = OneTimeWorkRequestBuilder<PriceMonitorWorker>()
        //    .setInitialDelay(15, TimeUnit.SECONDS) // Set delay here
        //   .build()
        //WorkManager.getInstance(this).enqueue(oneTimeRequest)

        enableEdgeToEdge()

        setContent {
            TechtrackrTheme(dynamicColor = false) {
                val navController = rememberNavController()
                val sharedDataViewModel: SharedDataViewModel = viewModel()

                // Determine start destination based on authentication status
                val startDestination = if (auth.currentUser != null) {
                    // Preload data if user is authenticated
                    sharedDataViewModel.preloadData()
                    "home"
                } else {
                    "auth"
                }

                CompositionLocalProvider(
                    LocalNavController provides navController,
                    LocalSharedDataViewModel provides sharedDataViewModel
                ) {
                    AppNavHost(
                        navController = navController,
                        startDestination = startDestination,
                        auth = auth
                    )
                }
            }
        }
    }

    private fun setupPriceMonitorWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val priceMonitorRequest = PeriodicWorkRequestBuilder<PriceMonitorWorker>(6, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "PriceMonitorWork",
            ExistingPeriodicWorkPolicy.KEEP,
            priceMonitorRequest
        )
    }

}

