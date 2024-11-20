package com.example.techtrackr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.techtrackr.ui.theme.TechTrackrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechTrackrTheme {
                // Set up the navigation controller
                val navController = rememberNavController()
                // NavHost to manage the screens
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        // Pass the navController to the LoginScreen
                        LoginScreen(navController = navController)
                    }
                    composable("main") {
                        MainScreen()
                    }
                }
            }
        }
    }
}
