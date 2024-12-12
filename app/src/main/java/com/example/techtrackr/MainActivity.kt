// MainActivity.kt
package com.example.techtrackr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.techtrackr.ui.home.HomeScreen
import com.example.techtrackr.ui.profile.ProfileScreen
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

                // Determine start destination based on authentication status
                val startDestination = if (auth.currentUser != null) "home" else "auth"

                NavHost(navController = navController, startDestination = startDestination) {


                    composable("auth") {
                        val viewModel: AuthViewModel = viewModel(
                            factory = AuthViewModelFactory(auth, applicationContext)
                        )

                        AuthenticationScreen(
                            viewModel = viewModel,
                            onNavigateToHome = { success ->
                                if (success) {
                                    navController.navigate("home") {
                                        popUpTo("auth") { inclusive = true }
                                    }
                                }
                            }
                        )
                    }

                    composable("home") {
                        HomeScreen(
                            onNavigateToProfile = {
                                navController.navigate("profile")
                            }
                        )
                    }

                    composable("profile") {
                        ProfileScreen(
                            onLogout = {
                                auth.signOut()
                                navController.navigate("auth") {
                                    popUpTo("auth") { inclusive = true }
                                }
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
