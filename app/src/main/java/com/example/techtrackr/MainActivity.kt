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
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat


class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Define the permission launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("MainActivity", "POST_NOTIFICATIONS permission granted.")
            // You can trigger any actions that require the permission here if needed
        } else {
            Log.d("MainActivity", "POST_NOTIFICATIONS permission denied.")
            // Optionally, inform the user that notifications won't be available
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesUtils.init(applicationContext)
        setupPriceMonitorWorker()

        // Request notification permission
        requestNotificationPermission()

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

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission is already granted
                    Log.d("MainActivity", "POST_NOTIFICATIONS permission already granted.")
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    // Show an explanation to the user *asynchronously*
                    Log.d("MainActivity", "Showing rationale for POST_NOTIFICATIONS permission.")
                    // You can show a dialog or a snackbar here to explain why the permission is needed
                    // After showing the rationale, request the permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                else -> {
                    // Directly request the permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
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


