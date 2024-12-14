package com.example.techtrackr.ui.product

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techtrackr.data.model.Category
import com.example.techtrackr.data.model.Image
import com.example.techtrackr.data.model.PdPrice
import com.example.techtrackr.data.model.PriceHistoryResponse
import com.example.techtrackr.data.model.Product
import com.example.techtrackr.data.model.ProductDetailsResponse
import com.example.techtrackr.data.model.ProductListingsResponse
import com.example.techtrackr.data.model.Ribbon
import com.example.techtrackr.data.repository.ProductRepository
import com.example.techtrackr.data.remote.NetworkModule
import com.example.techtrackr.data.shared.SharedDataViewModel
import com.example.techtrackr.ui.watchlist.WatchlistProduct
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProductViewModel(
    subcategoryId: String,
    productId: String,
    private val sharedDataViewModel: SharedDataViewModel,
    private val context: Context
) : ViewModel() {

    private val repository = ProductRepository(NetworkModule.apiService)

    private val _productDetailsState = MutableStateFlow<ProductDetailsResponse?>(null)
    val productDetailsState = _productDetailsState.asStateFlow()

    private val _productListingsState = MutableStateFlow<ProductListingsResponse?>(null)
    val productListingsState = _productListingsState.asStateFlow()

    private val _priceHistoryState = MutableStateFlow<PriceHistoryResponse?>(null)
    val priceHistoryState = _priceHistoryState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    //Watch list
    private val _isInWatchlist = MutableStateFlow(false)
    val isInWatchlist: StateFlow<Boolean> = _isInWatchlist
    private val firestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        loadProductData(subcategoryId, productId)
        checkIfInWatchlist(productId)
    }

    private fun loadProductData(subcategoryId: String, productId: String) {
        viewModelScope.launch {
            Log.d("ProductViewModel", "Loading product data for subcategoryId: $subcategoryId, productId: $productId")
            _isLoading.value = true
            _errorMessage.value = null

            var details: ProductDetailsResponse? = null
            var listings: ProductListingsResponse? = null
            var priceHistory: PriceHistoryResponse? = null

            try {
                details = repository.getProductDetails(subcategoryId, productId)
                Log.d("ProductViewModel", "Product details: $details")

                listings = repository.getProductListings(productId)
                Log.d("ProductViewModel", "Product listings: $listings")

                priceHistory = repository.getPriceHistory(productId)
                Log.d("ProductViewModel", "Price history: $priceHistory")

                _productDetailsState.value = details
                _productListingsState.value = listings
                _priceHistoryState.value = priceHistory

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

    private fun checkIfInWatchlist(productId: String) {
        val currentUser = auth.currentUser
        Log.d("ProductViewModel", "Checking watchlist for productId: $productId. User: ${currentUser?.uid}, isAnonymous: ${currentUser?.isAnonymous}")
        if (currentUser == null || currentUser.isAnonymous) {
            _isInWatchlist.value = false
            return
        }

        val watchlistDocRef = firestore.collection("users")
            .document(currentUser.uid)
            .collection("watchlist")
            .document(productId)

        watchlistDocRef.get()
            .addOnSuccessListener { document ->
                _isInWatchlist.value = document.exists()
            }
            .addOnFailureListener { exception ->
                Log.e("ProductViewModel", "Error checking watchlist: ${exception.message}")
            }
    }

    fun addToWatchlist() {
        val currentUser = auth.currentUser
        Log.d("ProductViewModel", "Attempting to add to watchlist. User: ${currentUser?.uid}, isAnonymous: ${currentUser?.isAnonymous}")
        if (currentUser == null || currentUser.isAnonymous) {
            // Handle unauthenticated or guest user
            _errorMessage.value = "You must be logged in to add to watchlist."
            return
        }

        val product = createProductFromDetailsAndListings(
            _productDetailsState.value,
            _productListingsState.value,
            subcategoryId = _productDetailsState.value?.category?.id ?: "",
            productId = _productDetailsState.value?.product?.id ?: "",
        ) ?: run {
            _errorMessage.value = "Product information is incomplete."
            return
        }

        // Assuming `lowestPrice.amount` is the current price
        val originalPrice = product.lowestPrice?.amount?.toDoubleOrNull() ?: 0.0

        val watchlistDocRef = firestore.collection("users")
            .document(currentUser.uid)
            .collection("watchlist")
            .document(product.id)

        val watchlistProduct = WatchlistProduct(
            name = product.name,
            url = product.url,
            categoryID = product.category.id,
            productID = product.id,
            description = product.description,
            currentPrice = originalPrice,
            imageUrl = product.image?.path,
            timestamp = Timestamp.now(),
            originalPrice = originalPrice // Set original price
        )

        firestore.runTransaction { transaction ->
            transaction.set(watchlistDocRef, watchlistProduct)
        }
            .addOnSuccessListener {
                _isInWatchlist.value = true
                toast("Added to watchlist")
            }
            .addOnFailureListener { exception ->
                Log.e("ProductViewModel", "Error adding to watchlist: ${exception.message}")
                _errorMessage.value = "Failed to add to watchlist."
            }
    }


    fun removeFromWatchlist() {
        val currentUser = auth.currentUser
        Log.d("ProductViewModel", "Attempting to remove from watchlist. User: ${currentUser?.uid}, isAnonymous: ${currentUser?.isAnonymous}")
        if (currentUser == null || currentUser.isAnonymous) {
            // Handle unauthenticated or guest user
            _errorMessage.value = "You must be logged in to remove from watchlist."
            return
        }

        val productId = _productDetailsState.value?.product?.id ?: return

        val watchlistDocRef = firestore.collection("users")
            .document(currentUser.uid)
            .collection("watchlist")
            .document(productId)

        watchlistDocRef.delete()
            .addOnSuccessListener {
                _isInWatchlist.value = false
            }
            .addOnFailureListener { exception ->
                Log.e("ProductViewModel", "Error removing from watchlist: ${exception.message}")
                _errorMessage.value = "Failed to remove from watchlist."
            }

        toast("Removed from watchlist")
    }

    private fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
