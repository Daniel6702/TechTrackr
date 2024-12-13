package com.example.techtrackr.ui.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techtrackr.data.model.Category
import com.example.techtrackr.data.model.Image
import com.example.techtrackr.data.model.PdPrice
import com.example.techtrackr.data.model.Product
import com.example.techtrackr.data.model.ProductDetailsResponse
import com.example.techtrackr.data.model.ProductListingsResponse
import com.example.techtrackr.data.model.Ribbon
import com.example.techtrackr.data.repository.ProductRepository
import com.example.techtrackr.data.remote.NetworkModule
import com.example.techtrackr.data.shared.SharedDataViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    subcategoryId: String,
    productId: String,
    private val sharedDataViewModel: SharedDataViewModel
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

            var details: ProductDetailsResponse? = null
            var listings: ProductListingsResponse? = null

            try {
                details = repository.getProductDetails(subcategoryId, productId)
                Log.d("ProductViewModel", "Product details: $details")

                listings = repository.getProductListings(productId)
                Log.d("ProductViewModel", "Product listings: $listings")

                _productDetailsState.value = details
                _productListingsState.value = listings

                // Convert product details and listings to a Product object
                val viewedProduct = createProductFromDetailsAndListings(details, listings, subcategoryId, productId)
                if (viewedProduct != null) {
                    sharedDataViewModel.addRecentlyViewed(viewedProduct)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "Failed to load product data."
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun createProductFromDetailsAndListings(
        details: ProductDetailsResponse?,
        listings: ProductListingsResponse?,
        subcategoryId: String,
        productId: String
    ): Product? {
        val productInfo = details?.product ?: return null
        val productCategory = details.category

        // Determine lowest price from listings
        val lowestOffer = listings?.offers?.minByOrNull { offer ->
            offer.price?.amount?.toDoubleOrNull() ?: Double.MAX_VALUE
        }

        val lowestPrice = lowestOffer?.price?.let { price ->
            PdPrice(amount = price.amount ?: "", currency = price.currency ?: "")
        }

        // Construct category
        val categoryId = productCategory?.id ?: "cl$subcategoryId"
        val category = Category(
            id = categoryId,
            name = productCategory?.name ?: "Unknown Category",
            url = productCategory?.url
        )

        // Pick first image as main image if available
        val mainImage = productInfo.images?.firstOrNull()?.let { img ->
            Image(
                id = img.id,
                url = null, // `url` not provided, just path
                path = img.path ?: "",
                description = img.description
            )
        }

        // Ribbon is not part of Product model for recently viewed? If needed:
        val ribbon = productInfo.ribbon?.let {
            Ribbon(
                type = it.type,
                value = it.value,
                description = null
            )
        }

        // Brand, rating, priceDrop, previewMerchants, classification are not available from product details
        // or we need more logic to extract them. Set them to null for now.
        return Product(
            id = productId,
            name = productInfo.name ?: "Unknown",
            url = productInfo.url,
            description = productInfo.description,
            lowestPrice = lowestPrice,
            category = category,
            image = mainImage,
            rating = null,      // No rating info in ProductDetailsResponse
            priceDrop = null,   // No price drop info available
            brand = null,       // brand info might be in ProductBrand if needed
            ribbon = ribbon,
            classification = null,
            previewMerchants = null
        )
    }


}
