package com.example.techtrackr.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private var _searchQuery = mutableStateOf("")
    val searchQuery: String get() = _searchQuery.value

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        Log.d("HomeViewModel", "Search query changed: $newQuery")
    }

    fun performSearch() {
        Log.d("HomeViewModel", "Performing search for: ${_searchQuery.value}")
    }
}
