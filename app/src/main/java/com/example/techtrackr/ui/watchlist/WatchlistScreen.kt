package com.example.techtrackr.ui.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.techtrackr.ui.navigation.CommonNavigationLayout
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.techtrackr.ui.navigation.LocalNavController

@Composable
fun WatchlistScreen(
    watchlistViewModel: WatchlistViewModel = viewModel()
) {
    val watchlist by watchlistViewModel.watchlist.collectAsState()
    val isLoading by watchlistViewModel.isLoading.collectAsState()
    val errorMessage by watchlistViewModel.errorMessage.collectAsState()

    val navController = LocalNavController.current

    CommonNavigationLayout(
        title = "Watchlist"
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage != null -> {
                    Text(
                        text = errorMessage ?: "An unknown error occurred.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                watchlist.isEmpty() -> {
                    Text(
                        text = "Your watchlist is empty.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(watchlist) { product ->
                            WatchlistProductCard(
                                product = product,
                                onRemoveClick = { productId ->
                                    watchlistViewModel.removeFromWatchlist(productId)
                                },
                                onProductClick = { watchlistProduct ->
                                    // Navigate to the product page using subcategoryID and productID
                                    navController.navigate("product/${watchlistProduct.categoryID}/${watchlistProduct.productID}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


