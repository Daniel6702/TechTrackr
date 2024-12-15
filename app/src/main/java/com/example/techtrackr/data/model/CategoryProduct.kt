package com.example.techtrackr.data.model

data class CategoryProductsResponse (
    val totalHits: Int?,
    val products: List<CategoryProduct>
)

data class CategoryProduct(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val lowestPrice: PdPrice?,
    val image: Image?,
    val rank: Rank?,
    val brand: Brand?,
    val rating: Rating?,
    val ribbon: Ribbon?,
    val productGroup: ProductGroup?,
    val cheapestOffer: CheapestOffer?,
    val previewMerchants: PreviewMerchants,
    val installmentPrice: Price?
)

data class CheapestOffer(
    val id: String,
    val price: Price,
    val url: String,
    val merchant: Merchant,
    val pricePerUnit: Price?,
)