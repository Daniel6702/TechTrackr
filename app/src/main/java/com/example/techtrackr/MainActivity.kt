package com.example.techtrackr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.techtrackr.data.shared.LocalSharedDataViewModel
import com.example.techtrackr.data.shared.SharedDataViewModel
import com.example.techtrackr.ui.navigation.AppNavHost
import com.example.techtrackr.ui.navigation.LocalNavController
import com.example.techtrackr.ui.theme.TechtrackrTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TechtrackrTheme {
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
}

