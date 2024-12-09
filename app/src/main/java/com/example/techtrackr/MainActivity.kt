package com.example.techtrackr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.compose.*
import com.example.authenticationtesting.ui.theme.TechtrackrTheme


class MainActivity : ComponentActivity() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechtrackrTheme {
                // Set up Navigation
                val navController = rememberNavController()

                // Navigation host (the main entry point for composables)
                NavHost(navController = navController, startDestination = "auth") {
                    composable("auth") {
                        // Authentication screen Composable
                        AuthenticationScreen(
                            auth = auth,
                            context = this@MainActivity, // Pass context to AuthenticationScreen
                            onLoginSuccess = {
                                // Navigate to Home Screen after successful login
                                navController.navigate("home") {
                                    // Pop up to the "auth" screen to prevent back navigation
                                    popUpTo("auth") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("home") {
                        // Home Screen Composable
                        HomeScreen(
                            onLogout = {
                                auth.signOut()
                                navController.navigate("auth") {
                                    popUpTo("auth") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
