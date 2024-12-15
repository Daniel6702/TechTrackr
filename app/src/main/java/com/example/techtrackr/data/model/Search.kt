package com.example.techtrackr.data.model

data class SearchResponse(
    val searchQuery: String?,
    val products: List<SearchProduct>?,
    val categoryOffers: List<Any>?, // Assuming empty or unknown structure
    val pages: List<Any>?,          // Assuming empty or unknown structure
    val boards: List<Any>?,         // Assuming empty or unknown structure
    val numberOfHits: NumberOfHits?,
    val categories: List<Any>?,     // Assuming empty or unknown structure
    val brands: List<Any>?,         // Assuming empty or unknown structure
    val merchants: List<Any>?,      // Assuming empty or unknown structure
    val featureSuggestions: List<Any>?,
    val spellingSuggestion: String?,
    val adsCategoryId: String?,
    val order: List<String>?,
    val head: Head?,
    val klarnaMerchants: List<Any>?,
    val source: String?,
    val productseries: List<Any>?
)

data class SearchProduct(
    val id: String?,
    val name: String?,
    val url: String?,
    val description: String?,
    val lowestPrice: Price?,
    val category: Category?,
    val image: Image?,
    val rank: Rank?,
    val rating: Rating?,
    val priceDrop: Any?, // Assuming nullable or unknown structure
    val brand: Any?,      // Assuming nullable or unknown structure
    val ribbon: Ribbon?,
    val classification: String?,
    val previewMerchants: PreviewMerchants?,
    val installmentPrice: Any? // Assuming nullable or unknown structure
)

data class Price(
    val amount: String?,
    val currency: String?
)

data class Rank(
    val rank: Int?,
    val trend: String?
)

data class NumberOfHits(
    val total: Int?,
    val product: Int?,
    val categoryOffer: Int?
)

data class Head(
    val title: String?,
    val description: String?,
    val contentLanguage: String?,
    val robots: String?,
    val canonicalUrl: String?,
    val og: OpenGraph?
)

data class OpenGraph(
    val ogTitle: String?,
    val ogDescription: String?,
    val ogSiteName: String?,
    val ogUrl: String?,
    val ogImage: String?
)
