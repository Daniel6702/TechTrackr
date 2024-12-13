package com.example.techtrackr.ui.product

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

@Composable
fun ProductPage(subcategoryId: String, productId: String) {
    val productViewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        key = "$subcategoryId-$productId",
        factory = ProductViewModelFactory(subcategoryId, productId)
    )

    ProductContent(productViewModel = productViewModel)
}

// A simple ViewModelFactory
class ProductViewModelFactory(
    private val subcategoryId: String,
    private val productId: String
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(subcategoryId, productId) as T
    }
}
