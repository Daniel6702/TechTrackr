package com.example.techtrackr.data.model

data class ProductListingsResponse(
    val filters: List<ListingFilter>?,
    val images: List<ProductImage>?,
    val merchants: Map<String, ListingMerchant>?,
    val offers: List<ListingOffer>?,
    val excludedOffers: List<ListingOffer>?
)

data class ListingFilter(
    val id: String?,
    val name: String?,
    val filterOptions: List<ListingFilterOption>?,
    val type: String?
)

data class ListingFilterOption(
    val id: String?,
    val name: String?,
    val count: Int?,
    val lowestPrice: ProductPrice?,
    val state: String?
)

data class ListingMerchant(
    val id: String?,
    val url: String?,
    val name: String?,
    val logo: MerchantLogo?,
    val countryCode: String?,
    val rating: MerchantRating?,
    val labels: List<String>?,
    val certificates: List<MerchantCertificate>?,
    val paymentMethods: List<MerchantPaymentMethod>?
)

data class MerchantLogo(
    val name: String?,
    val path: String?,
    val url: String?
)

data class MerchantRating(
    val count: Int?,
    val average: String?
)

data class MerchantCertificate(
    val id: String?,
    val name: String?,
    val logo: String?,
    val order: Int?
)

data class MerchantPaymentMethod(
    val id: String?,
    val name: String?,
    val logo: String?,
    val order: Int?
)

data class ListingOffer(
    val id: String?,
    val url: String?,
    val name: String?,
    val stockStatus: String?,
    val availability: String?,
    val deliveryTime: DeliveryTime?,
    val shippingCost: ProductPrice?,
    val price: ProductPrice?,
    val merchantId: String?,
    val labels: ListingLabels?,
    val pricePerUnit: ProductPrice?,
    val installmentPrice: ProductPrice?,
    val campaignPrice: ProductPrice?
)

data class DeliveryTime(
    val minDays: Int?,
    val maxDays: Int?
)

data class ListingLabels(
    val propertyLabels: List<String>?,
    val attributeLabels: List<AttributeLabel>?
)

data class AttributeLabel(
    val name: String?,
    val value: String?
)
