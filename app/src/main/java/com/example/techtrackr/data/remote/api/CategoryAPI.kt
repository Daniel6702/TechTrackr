package com.example.techtrackr.data.remote.api

import com.example.techtrackr.data.remote.transport.HTTPClient

import android.util.Log
import org.json.JSONObject
import java.io.IOException

/**
 * The CategoryAPI class provides methods to interact with category-related endpoints.
 * It utilizes the HTTPClient singleton for making HTTP GET requests with built-in retry logic and error handling.
 */
class CategoryAPI {

    /**
     * Retrieves the top-level categories.
     *
     * @return A JSONObject containing the top categories or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getTopCategories(): JSONObject? {
        val endpoint = "navigation/menu/DK/items"
        Log.d("CategoryAPI", "Fetching top categories from endpoint: $endpoint")
        return HTTPClient.get(endpoint)
    }

    /**
     * Retrieves all categories.
     *
     * @return A JSONObject containing all categories or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getAllCategories(): JSONObject? {
        val endpoint = "navigation/menu/DK"
        Log.d("CategoryAPI", "Fetching all categories from endpoint: $endpoint")
        return HTTPClient.get(endpoint)
    }

    /**
     * Retrieves extra content for a tree page based on category ID.
     *
     * @param categoryId The category ID.
     * @return A JSONObject containing the extra content or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getTreePageExtraContent(categoryId: String): JSONObject? {
        val endpoint = "cms"
        val params = mapOf(
            "contentType" to "treePageExtraContent",
            "id" to categoryId,
            "language" to "da"
        )
        Log.d("CategoryAPI", "Fetching tree page extra content from endpoint: $endpoint with params: $params")
        return HTTPClient.get(endpoint, params)
    }

    /**
     * Retrieves boards for a subcategory.
     *
     * @param subcategoryId The subcategory ID.
     * @param size The number of boards to retrieve (default is 4).
     * @return A JSONObject containing the boards or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getBoards(subcategoryId: String, size: Int = 4): JSONObject? {
        val endpoint = "search/board/DK/$subcategoryId"
        val params = mapOf("size" to size.toString())
        Log.d("CategoryAPI", "Fetching boards from endpoint: $endpoint with params: $params")
        return HTTPClient.get(endpoint, params)
    }

    /**
     * Retrieves category data based on category ID.
     *
     * @param categoryId The category ID.
     * @return A JSONObject containing category data or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getCategoryData(categoryId: String): JSONObject? {
        val endpoint = "navigation/menu/DK/hierarchy/$categoryId"
        Log.d("CategoryAPI", "Fetching category data from endpoint: $endpoint")
        return HTTPClient.get(endpoint)
    }

    /**
     * Retrieves breadcrumbs for a category based on category ID.
     *
     * @param categoryId The category ID.
     * @return A JSONObject containing breadcrumbs or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getBreadcrumbs(categoryId: String): JSONObject? {
        val endpoint = "navigation/breadcrumbs/DK/$categoryId"
        Log.d("CategoryAPI", "Fetching breadcrumbs from endpoint: $endpoint")
        return HTTPClient.get(endpoint)
    }

    /**
     * Retrieves keywords for a category based on category ID.
     *
     * @param categoryId The category ID.
     * @return A JSONObject containing keywords or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getKeywords(categoryId: String): JSONObject? {
        val endpoint = "keyword/tree/DK/$categoryId"
        Log.d("CategoryAPI", "Fetching keywords from endpoint: $endpoint")
        return HTTPClient.get(endpoint)
    }

    /**
     * Retrieves keywords for a subcategory based on subcategory ID.
     *
     * @param subcategoryId The subcategory ID.
     * @return A JSONObject containing subcategory keywords or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getKeywordsSub(subcategoryId: String): JSONObject? {
        val endpoint = "keyword/category/DK/$subcategoryId"
        Log.d("CategoryAPI", "Fetching subcategory keywords from endpoint: $endpoint")
        return HTTPClient.get(endpoint)
    }

    /**
     * Retrieves popular products for a category based on category ID.
     *
     * @param categoryId The category ID.
     * @return A JSONObject containing popular products or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getPopularProducts(categoryId: String): JSONObject? {
        val endpoint = "popularproducts/v2/DK/$categoryId"
        Log.d("CategoryAPI", "Fetching popular products from endpoint: $endpoint")
        return HTTPClient.get(endpoint)
    }

    /**
     * Retrieves products for a subcategory with optional filters.
     *
     * @param subcategoryId The subcategory ID.
     * @param size The number of products to retrieve (default is 10).
     * @param filters A list of filters in the format "key=value".
     * @return A JSONObject containing products or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getProducts(subcategoryId: String, size: Int = 10, filters: List<String> = emptyList()): JSONObject? {
        val filterMap = mutableMapOf<String, String>()
        for (filterItem in filters) {
            val (key, value) = filterItem.split("=")
            filterMap[key] = value
        }
        val params = mutableMapOf("size" to size.toString())
        params.putAll(filterMap)
        val endpoint = "search/category/v3/DK/$subcategoryId"
        Log.d("CategoryAPI", "Fetching products from endpoint: $endpoint with params: $params")
        return HTTPClient.get(endpoint, params)
    }

    /**
     * Retrieves guiding content for a subcategory.
     *
     * @param subcategoryId The subcategory ID.
     * @param size The number of content items to retrieve (default is 10).
     * @return A JSONObject containing guiding content or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun getGuidingContent(subcategoryId: String, size: Int = 10): JSONObject? {
        val endpoint = "search/guidingcontent/v2/DK/$subcategoryId"
        val params = mapOf("size" to size.toString())
        Log.d("CategoryAPI", "Fetching guiding content from endpoint: $endpoint with params: $params")
        return HTTPClient.get(endpoint, params)
    }
}
