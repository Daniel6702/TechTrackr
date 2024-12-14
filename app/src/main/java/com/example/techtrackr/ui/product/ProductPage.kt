package com.example.techtrackr.ui.product

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.techtrackr.data.shared.LocalSharedDataViewModel
import com.example.techtrackr.data.shared.SharedDataViewModel

@Composable
fun ProductPage(
    subcategoryId: String,
    productId: String,
    sharedDataViewModel: SharedDataViewModel = LocalSharedDataViewModel.current
) {

    val context = LocalContext.current

    val productViewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        key = "$subcategoryId-$productId",
        factory = ProductViewModelFactory(subcategoryId, productId, sharedDataViewModel, context)
    )

    ProductContent(productViewModel = productViewModel)
}

// A simple ViewModelFactory
class ProductViewModelFactory(
    private val subcategoryId: String,
    private val productId: String,
    private val sharedDataViewModel: SharedDataViewModel,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(subcategoryId, productId, sharedDataViewModel, context) as T
    }
}
