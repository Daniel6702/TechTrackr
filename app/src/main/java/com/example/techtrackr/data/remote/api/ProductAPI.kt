package com.example.techtrackr.data.remote

import android.util.Log
import com.example.techtrackr.data.remote.transport.HTTPClient
import org.json.JSONObject
import java.io.IOException

/**
 * The ProductAPI class provides various methods to interact with product-related endpoints.
 * It utilizes the HTTPClient singleton for making HTTP GET requests with built-in retry logic and error handling.
 */
class ProductAPI {

    /**
     * Retrieves product details based on subcategory ID and product ID.
     *
     * @param subcategoryId The subcategory ID of the product.
     * @param productId The product ID.
     * @return A JSONObject containing product details or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getProductDetails(subcategoryId: String, productId: String): JSONObject? {
        val endpoint = "productlistings/pl/initial/${subcategoryId}-${productId}/DK"
        Log.d("ProductAPI", "Fetching product details from endpoint: $endpoint")
        return HTTPClient.get(endpoint)
    }

    /**
     * Retrieves the rank of a product based on its product ID.
     *
     * @param productId The product ID.
     * @return A JSONObject containing product rank or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getProductRank(productId: String): JSONObject? {
        val endpoint = "productlistings/rank/DK/$productId"
        Log.d("ProductAPI", "Fetching product rank from endpoint: $endpoint")
        return HTTPClient.get(endpoint)
    }

    /**
     * Retrieves keywords associated with a product based on subcategory ID and product ID.
     *
     * @param subcategoryId The subcategory ID of the product.
     * @param productId The product ID.
     * @return A JSONObject containing product keywords or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getProductKeywords(subcategoryId: String, productId: String): JSONObject? {
        val endpoint = "keyword/product/DK/${subcategoryId}-${productId}"
        Log.d("ProductAPI", "Fetching product keywords from endpoint: $endpoint")
        return HTTPClient.get(endpoint)
    }

    /**
     * Retrieves offers for a specific product.
     *
     * @param productId The product ID.
     * @param merchantId (Optional) The merchant ID.
     * @param af_ORIGIN The origin filter (default is "NATIONAL").
     * @param af_ITEM_CONDITION The item condition filter (default is "NEW,UNKNOWN").
     * @param sortByPreset The sorting preset (default is "PRICE").
     * @return A JSONObject containing product offers or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getProductOffers(
        productId: String,
        merchantId: String? = null,
        af_ORIGIN: String = "NATIONAL",
        af_ITEM_CONDITION: String = "NEW,UNKNOWN",
        sortByPreset: String = "PRICE"
    ): JSONObject? {
        val endpoint = "product-detail/v0/offers/DK/$productId"
        val params = mutableMapOf(
            "af_ORIGIN" to af_ORIGIN,
            "af_ITEM_CONDITION" to af_ITEM_CONDITION,
            "sortByPreset" to sortByPreset
        )
        merchantId?.let {
            params["af_MERCHANT"] = it
        }
        Log.d("ProductAPI", "Fetching product offers from endpoint: $endpoint with params: $params")
        return HTTPClient.get(endpoint, params)
    }

    /**
     * Retrieves the price history of a product.
     *
     * @param productId The product ID.
     * @param selectedInterval The selected interval for price history (default is "THREE_MONTHS").
     * @param merchantId The merchant ID (default is an empty string for all merchants).
     * @return A JSONObject containing price history or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getPriceHistory(
        productId: String,
        selectedInterval: String = "THREE_MONTHS",
        merchantId: String = ""
    ): JSONObject? {
        val endpoint = "pricehistory/product/$productId/DK/DAY"
        val params = mapOf(
            "merchantId" to merchantId,
            "selectedInterval" to selectedInterval,
            "filter" to "NATIONAL"
        )
        Log.d("ProductAPI", "Fetching price history from endpoint: $endpoint with params: $params")
        return HTTPClient.get(endpoint, params)
    }

    /**
     * Retrieves reviews for a product.
     *
     * @param productId The product ID.
     * @param count The number of reviews to retrieve (default is 4).
     * @return A JSONObject containing product reviews or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getProductReviews(productId: String, count: Int = 4): JSONObject? {
        val endpoint = "reviews/products/overview/DK/$productId"
        val params = mapOf("count" to count.toString())
        Log.d("ProductAPI", "Fetching product reviews from endpoint: $endpoint with params: $params")
        return HTTPClient.get(endpoint, params)
    }

    /**
     * Lists multiple products based on a list of product IDs.
     *
     * @param productIds The list of product IDs.
     * @return A JSONObject containing the list of products or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun listProducts(productIds: List<String>): JSONObject? {
        val endpoint = "listings/products/DK"
        val params = mapOf("productIds" to productIds.joinToString(","))
        Log.d("ProductAPI", "Listing products from endpoint: $endpoint with params: $params")
        return HTTPClient.get(endpoint, params)
    }

    /**
     * Lists product information with various filtering options.
     *
     * @param productIds The list of product IDs.
     * @param withShipping Whether to include shipping information (default is false).
     * @param onlyPayingMerchants Whether to include only paying merchants (default is false).
     * @param onlyCertifiedMerchants Whether to include only certified merchants (default is false).
     * @return A JSONObject containing product information or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun listProductsInfo(
        productIds: List<String>,
        withShipping: Boolean = false,
        onlyPayingMerchants: Boolean = false,
        onlyCertifiedMerchants: Boolean = false
    ): JSONObject? {
        val endpoint = "productinfo/DK"
        val params = mutableMapOf(
            "productIds" to productIds.joinToString(","),
            "withShipping" to withShipping.toString(),
            "onlyPayingMerchants" to onlyPayingMerchants.toString(),
            "onlyCertifiedMerchants" to onlyCertifiedMerchants.toString()
        )
        Log.d("ProductAPI", "Listing products info from endpoint: $endpoint with params: $params")
        return HTTPClient.get(endpoint, params)
    }

    /**
     * Retrieves a list of hot products.
     *
     * @param size The number of hot products to retrieve (default is 10).
     * @return A JSONObject containing hot products or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getHotProducts(size: Int = 10): JSONObject? {
        val endpoint = "hot/products/v2/DK"
        val params = mapOf("size" to size.toString())
        Log.d("ProductAPI", "Fetching hot products from endpoint: $endpoint with params: $params")
        return HTTPClient.get(endpoint, params)
    }
}
