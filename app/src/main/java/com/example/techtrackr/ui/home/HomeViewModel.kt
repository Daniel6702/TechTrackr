package com.example.techtrackr.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techtrackr.data.model.Product
import com.example.techtrackr.data.model.SearchProduct
import com.example.techtrackr.data.repository.CategoryRepository
import com.example.techtrackr.data.remote.NetworkModule
import com.example.techtrackr.utils.ALLOWED_CATEGORY_IDS
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = CategoryRepository(NetworkModule.apiService)

    // Search State
    private var _searchQuery = mutableStateOf("")
    val searchQuery: String get() = _searchQuery.value

    // Deals State
    var deals by mutableStateOf<List<Product>>(emptyList())
        private set

    // Hot Products State
    var hotProducts by mutableStateOf<List<Product>>(emptyList())
        private set

    var searchProducts: List<SearchProduct> by mutableStateOf(emptyList())
        private set

    // Constants for retry mechanism
    private val MAX_RETRIES = 10
    private val RETRY_DELAY_MS = 1000L

    init {
        loadDeals()
        loadHotProducts()
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        val len = newQuery.length
        // If length of query is 0, reset search
        if (newQuery.isEmpty() || newQuery.isBlank() || len <= 1) {
            searchProducts = emptyList()
            return
        }
        Log.d("HomeViewModel", "Search query changed: $newQuery")

        // Trigger search when query changes
        performSearch()
    }

    fun performSearch() {
        val query = _searchQuery.value.lowercase()

        viewModelScope.launch {
            try {
                val response = repository.getSearch(query)

                searchProducts = response.products.orEmpty().filter { product ->
                    product.category?.id in ALLOWED_CATEGORY_IDS
                }

                Log.d("HomeViewModel", "Search query: $query, Found ${searchProducts.size} products after filtering")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HomeViewModel", "Error performing search: ${e.message}")
                searchProducts = emptyList()
            }
        }
    }

    private fun loadDeals() {
        viewModelScope.launch {
            var attempt = 0
            while (attempt <= MAX_RETRIES) {
                try {
                    Log.d("HomeViewModel", "Loading deals, attempt ${attempt + 1}")
                    val response = repository.getDeals()
                    // Filter the products by allowed category IDs
                    val filteredDeals = response.products.filter { product ->
                        ALLOWED_CATEGORY_IDS.contains(product.category.id)
                    }
                    if (filteredDeals.isNotEmpty()) {
                        deals = filteredDeals
                        Log.d("HomeViewModel", "Deals loaded successfully with ${deals.size} items")
                        break
                    } else {
                        Log.d("HomeViewModel", "Deals empty on attempt ${attempt + 1}")
                        if (attempt < MAX_RETRIES) {
                            delay(RETRY_DELAY_MS)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("HomeViewModel", "Error loading deals on attempt ${attempt + 1}: ${e.message}")
                    if (attempt >= MAX_RETRIES) {
                        deals = emptyList()
                    } else {
                        delay(RETRY_DELAY_MS)
                    }
                }
                attempt++
            }
        }
    }

    private fun loadHotProducts() {
        viewModelScope.launch {
            var attempt = 0
            while (attempt <= MAX_RETRIES) {
                try {
                    Log.d("HomeViewModel", "Loading hot products, attempt ${attempt + 1}")
                    val response = repository.getHotProducts()
                    // Filter by allowed categories
                    val filteredHot = response.products.filter { product ->
                        ALLOWED_CATEGORY_IDS.contains(product.category.id)
                    }
                    if (filteredHot.isNotEmpty()) {
                        hotProducts = filteredHot
                        Log.d("HomeViewModel", "Hot products loaded successfully with ${hotProducts.size} items")
                        break
                    } else {
                        Log.d("HomeViewModel", "Hot products empty on attempt ${attempt + 1}")
                        if (attempt < MAX_RETRIES) {
                            delay(RETRY_DELAY_MS)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("HomeViewModel", "Error loading hot products on attempt ${attempt + 1}: ${e.message}")
                    if (attempt >= MAX_RETRIES) {
                        hotProducts = emptyList()
                    } else {
                        delay(RETRY_DELAY_MS)
                    }
                }
                attempt++
            }
        }
    }
}
