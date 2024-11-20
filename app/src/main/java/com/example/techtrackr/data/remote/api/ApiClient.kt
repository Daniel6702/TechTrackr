package com.example.techtrackr.data.remote.api

import com.example.techtrackr.data.remote.ProductAPI
import org.json.JSONObject
import java.io.IOException

/**
 * The Client singleton combines the functionalities of ProductAPI and SearchAPI.
 * It provides a unified interface for API interactions, simplifying their usage across the application.
 */
object Client {

    // Instances of ProductAPI and SearchAPI
    private val productAPI = ProductAPI()
    private val searchAPI = SearchAPI()

    // ===========================
    // ProductAPI Methods Delegation
    // ===========================

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
        return productAPI.getProductDetails(subcategoryId, productId)
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
        return productAPI.getProductRank(productId)
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
        return productAPI.getProductKeywords(subcategoryId, productId)
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
        return productAPI.getProductOffers(productId, merchantId, af_ORIGIN, af_ITEM_CONDITION, sortByPreset)
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
        return productAPI.getPriceHistory(productId, selectedInterval, merchantId)
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
        return productAPI.getProductReviews(productId, count)
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
        return productAPI.listProducts(productIds)
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
        return productAPI.listProductsInfo(productIds, withShipping, onlyPayingMerchants, onlyCertifiedMerchants)
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
        return productAPI.getHotProducts(size)
    }

    // ===========================
    // SearchAPI Methods Delegation
    // ===========================

    /**
     * Retrieves search suggestions based on the provided search query.
     *
     * @param searchQuery The search query string.
     * @return A JSONObject containing search suggestions or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun suggest(searchQuery: String): JSONObject? {
        return searchAPI.suggest(searchQuery)
    }

    /**
     * Performs a search based on the provided parameters.
     *
     * @param searchQuery The search query string.
     * @param size The number of results to retrieve (default is 10).
     * @param suggestionsActive Whether suggestions are active (default is true).
     * @param suggestionClicked Whether a suggestion was clicked (default is false).
     * @param suggestionReverted Whether a suggestion was reverted (default is false).
     * @return A JSONObject containing search results or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun search(
        searchQuery: String,
        size: Int = 10,
        suggestionsActive: Boolean = true,
        suggestionClicked: Boolean = false,
        suggestionReverted: Boolean = false
    ): JSONObject? {
        return searchAPI.search(searchQuery, size, suggestionsActive, suggestionClicked, suggestionReverted)
    }

    /**
     * Performs an asynchronous search based on the provided parameters.
     *
     * @param searchQuery The search query string.
     * @param size The number of results to retrieve (default is 10).
     * @param suggestionsActive Whether suggestions are active (default is true).
     * @param suggestionClicked Whether a suggestion was clicked (default is false).
     * @param suggestionReverted Whether a suggestion was reverted (default is false).
     * @return A JSONObject containing search results or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    suspend fun searchAsync(
        searchQuery: String,
        size: Int = 10,
        suggestionsActive: Boolean = true,
        suggestionClicked: Boolean = false,
        suggestionReverted: Boolean = false
    ): JSONObject? {
        return searchAPI.searchAsync(searchQuery, size, suggestionsActive, suggestionClicked, suggestionReverted)
    }

    // ===========================
    // Additional Combined Methods
    // ===========================

    /**
     * Example of a combined method that utilizes both ProductAPI and SearchAPI.
     * Adjust according to your specific needs.
     *
     * @param searchQuery The search query string.
     * @return A Pair containing search suggestions and search results.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getSearchData(searchQuery: String): Pair<JSONObject?, JSONObject?> {
        val suggestions = suggest(searchQuery)
        val results = search(searchQuery)
        return Pair(suggestions, results)
    }

    // You can add more combined methods or utility functions here as needed.
}
