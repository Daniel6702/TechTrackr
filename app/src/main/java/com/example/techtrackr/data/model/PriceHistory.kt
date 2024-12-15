package com.example.techtrackr.data.model

data class PriceHistoryResponse(
    val productId: String?,
    val lowest: Double?,
    val highest: Double?,
    val currencyCode: String?,
    val history: List<PriceHistoryEntry>?,
    val merchants: List<Merchant>?
)

data class PriceHistoryEntry(
    val merchantId: String?,
    val merchantName: String?,
    val offerId: String?,
    val timestamp: String?,
    val timestampEpoch: String?,
    val price: Double?
)

data class Merchant(
    val id: Int?,
    val name: String?
)
