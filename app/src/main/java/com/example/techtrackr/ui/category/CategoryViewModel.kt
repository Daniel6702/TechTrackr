package com.example.techtrackr.ui.category;

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techtrackr.data.model.CategoryProduct
import com.example.techtrackr.data.model.Filter
import com.example.techtrackr.data.model.FilterFacet
import com.example.techtrackr.data.remote.NetworkModule
import com.example.techtrackr.data.repository.CategoryRepository
import kotlinx.coroutines.launch
import kotlin.math.log

class CategoryViewModel : ViewModel() {
    private val repository = CategoryRepository(NetworkModule.apiService)

    // categoryId State
    private var _categoryId = mutableStateOf("")
    val categoryId: String get() = _categoryId.value

    // Parameters state
    private var _parameters = mutableStateOf<Map<String, String>>(emptyMap())
    val parameters: Map<String, String> get() = _parameters.value

    // Filter Facets state
    var facets by mutableStateOf<Map<String, FilterFacet>>(emptyMap())
        private set

    // Filters state
     var filters by mutableStateOf<List<Filter>>(emptyList())
        private set

    // Deal State
    var deals by mutableStateOf<List<CategoryProduct>>(emptyList())
        private set

    init {
        loadFilters()
        loadDeals()
    }

    fun setCategoryId(categoryId: String) {
        if (_categoryId.value != categoryId) {
            _categoryId.value = categoryId
            loadFilters()
            loadDeals()
        }
    }

    private fun serializeParameters(): String {
        return _parameters.value.entries.joinToString("&") { "af_${it.key}=${it.value}" }
    }

    fun setParameters(filterId: String, value: String) {
        _parameters.value = _parameters.value.toMutableMap().apply {
            if (value.isNotEmpty()) {
                this[filterId] = value // Add or update the parameter
            } else {
                this.remove(filterId) // Remove the parameter if the value is empty
            }
        }
    }

    fun resetParameters() {
        _parameters.value = emptyMap()
    }

    fun loadDeals() {
        Log.d("CategoryViewModel", "LOAD DEALS")
        val id = _categoryId.value.removePrefix("cl")
        val params = serializeParameters()

        if (id != "") {
            viewModelScope.launch {
                try {
                    val response = repository.getCategoryDeals(id, params)
                    Log.d("CategoryViewModel", "loadDeals: Response $response")
                    deals = response.products
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("CategoryViewModel", "loadDeals: Error retrieving products: ${e.message}")
                }
            }
        }
    }

    private fun loadFilters() {
        Log.d("CategoryViewModel", "LOAD FILTERS")
        val id = _categoryId.value.removePrefix("cl")

        if (id != "") {
            viewModelScope.launch {
                try {
                    val response = repository.getCategoryFilters(id)
                    Log.d("CategoryViewModel", "loadFilters: Response $response")
                    val popularFilters = response.groups[0].filters
                    Log.d("CategoryViewModel", "loadFilters: Popular Filters: $popularFilters")
                    filters = popularFilters
                    loadFacets()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("CategoryViewModel", "loadFilters: Error retrieving filters: ${e.message}")
                }
            }
        }
    }

    private fun loadFacets() {
        Log.d("CategoryViewModel", "LOAD FACETS")
        val id = _categoryId.value.removePrefix("cl")
        Log.d("CategoryViewModel", "loadFacets: Got filters: $filters")

        if (id != "") {
            for (filter in filters) {
                viewModelScope.launch {
                   try {
                       val response = repository.getFilterFacet(id, filter.id)
                       Log.d("CategoryViewModel", "loadFacets: Response $response")
                       facets = facets.toMutableMap().apply {
                           this[filter.id] = response.facet
                       }
                   } catch (e: Exception) {
                       e.printStackTrace()
                       Log.e("CategoryViewModel", "loadFacets: Error retrieving facet id: ${e.message}")
                   }
                }
            }
        }
    }

    companion object
}