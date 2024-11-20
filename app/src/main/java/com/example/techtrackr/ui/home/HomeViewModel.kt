package com.example.techtrackr.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.techtrackr.data.remote.api.Client

class HomeViewModel : ViewModel() {
    private var _searchQuery = mutableStateOf("")
    val searchQuery: String get() = _searchQuery.value

    private val apiClient = Client

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        Log.d("HomeViewModel", "Search query changed: $newQuery")
    }

    suspend fun performSearch() {
        Log.d("HomeViewModel", "Performing search for: ${_searchQuery.value}")
        val data = apiClient.searchAsync(_searchQuery.value)
        Log.d("HomeViewModel", "Search results: $data")
    }
}
