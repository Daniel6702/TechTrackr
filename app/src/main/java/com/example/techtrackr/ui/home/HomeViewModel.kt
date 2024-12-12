// HomeViewModel.kt
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

    suspend fun performSearch() {
        Log.d("HomeViewModel", "Performing search for: ${_searchQuery.value}")
        //val data_json = apiClient.searchAsync(_searchQuery.value)
        //val categories = data_json?.getJSONArray("categories")
        //for each category in categories convert it to a Category object

        //Log.d("HomeViewModel", "Search results: $data")
    }
}
