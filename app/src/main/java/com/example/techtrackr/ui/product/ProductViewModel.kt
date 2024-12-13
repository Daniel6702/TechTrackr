package com.example.techtrackr.ui.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techtrackr.data.model.ProductDetailsResponse
import com.example.techtrackr.data.model.ProductListingsResponse
import com.example.techtrackr.data.repository.ProductRepository
import com.example.techtrackr.data.remote.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    subcategoryId: String,
    productId: String
) : ViewModel() {

    private val repository = ProductRepository(NetworkModule.apiService)

    private val _productDetailsState = MutableStateFlow<ProductDetailsResponse?>(null)
    val productDetailsState = _productDetailsState.asStateFlow()

    private val _productListingsState = MutableStateFlow<ProductListingsResponse?>(null)
    val productListingsState = _productListingsState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        loadProductData(subcategoryId, productId)
    }

    private fun loadProductData(subcategoryId: String, productId: String) {
        viewModelScope.launch {
            Log.d("ProductViewModel", "Loading product data for subcategoryId: $subcategoryId, productId: $productId")
            _isLoading.value = true
            _errorMessage.value = null

            val details = repository.getProductDetails(subcategoryId, productId)
            Log.d("ProductViewModel", "Product details: $details")
            val listings = repository.getProductListings(productId)
            Log.d("ProductViewModel", "Product listings: $listings")
            _productDetailsState.value = details
            _productListingsState.value = listings


            _isLoading.value = false

        }
    }
}
