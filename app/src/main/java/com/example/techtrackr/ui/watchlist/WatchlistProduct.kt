package com.example.techtrackr.ui.watchlist

import com.example.techtrackr.data.model.PdPrice
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class WatchlistProduct(
    @PropertyName("name") val name: String = "",
    @PropertyName("url") val url: String? = null,
    @PropertyName("categoryID") val categoryID: String = "",
    @PropertyName("productID") val productID: String = "",
    @PropertyName("description") val description: String? = null,
    @PropertyName("currentPrice") val currentPrice: Double = 0.0,
    @PropertyName("imageUrl") val imageUrl: String? = null,
    @PropertyName("timestamp") val timestamp: Timestamp? = null,
    @PropertyName("originalPrice") val originalPrice: Double = 0.0
)