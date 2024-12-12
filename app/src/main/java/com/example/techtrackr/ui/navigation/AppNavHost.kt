package com.example.techtrackr.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.techtrackr.AuthViewModel
import com.example.techtrackr.AuthViewModelFactory
import com.example.techtrackr.AuthenticationScreen
import com.example.techtrackr.ui.home.HomeScreen
import com.example.techtrackr.ui.navigation.CommonNavigationLayout
import com.example.techtrackr.ui.profile.ProfileScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    auth: FirebaseAuth
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable("auth") {
            val viewModel: AuthViewModel = viewModel(
                factory = AuthViewModelFactory(auth, navController.context)
            )
            AuthenticationScreen(viewModel = viewModel)
        }

        composable("home") {
            HomeScreen()
        }

        composable("profile") {
            ProfileScreen(onLogout = { auth.signOut() })
        }
    }
}
