package com.example.techtrackr.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.techtrackr.ui.components.SearchBar
import kotlinx.coroutines.runBlocking

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            query = homeViewModel.searchQuery,
            onQueryChange = { query -> homeViewModel.onSearchQueryChanged(query) },
            onSearch = {
                runBlocking {
                    homeViewModel.performSearch()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Home Screen Content", style = MaterialTheme.typography.bodyLarge)
    }
}
