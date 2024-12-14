package com.example.techtrackr.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.techtrackr.AuthViewModel
import com.example.techtrackr.AuthViewModelFactory
import com.example.techtrackr.AuthenticationScreen
import com.example.techtrackr.ui.category.CategoryPage
import com.example.techtrackr.ui.home.HomeScreen
import com.example.techtrackr.ui.navigation.CommonNavigationLayout
import com.example.techtrackr.ui.product.ProductPage
import com.example.techtrackr.ui.profile.ProfileScreen
import com.example.techtrackr.ui.watchlist.WatchlistScreen
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
            ProfileScreen(auth = auth)
        }

        composable(
            route = "category/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            CategoryPage(categoryId)
        }

        composable("watchlist") {
            WatchlistScreen()
        }

        composable(
            route = "product/{subcategoryId}/{productId}",
            arguments = listOf(
                navArgument("subcategoryId") { type = NavType.StringType },
                navArgument("productId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val subcategoryId = backStackEntry.arguments?.getString("subcategoryId") ?: ""
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductPage(subcategoryId, productId)
        }
    }
}


