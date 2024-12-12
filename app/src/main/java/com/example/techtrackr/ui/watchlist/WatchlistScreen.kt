package com.example.techtrackr.ui.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.techtrackr.ui.navigation.CommonNavigationLayout

@Composable
fun WatchlistScreen(
    watchlistViewModel: WatchlistViewModel = viewModel()
) {
    CommonNavigationLayout(
        title = "Watchlist"
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Watchlist Screen Content", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
