package com.example.techtrackr.data.remote.api

import android.util.Log
import com.example.techtrackr.data.remote.transport.HTTPClient
import org.json.JSONObject
import java.io.IOException

/**
 * The SearchAPI class provides methods to interact with search-related endpoints.
 * It utilizes the HTTPClient singleton for making HTTP GET requests with built-in retry logic and error handling.
 */
class SearchAPI {

    /**
     * Retrieves search suggestions based on the provided search query.
     *
     * @param searchQuery The search query string.
     * @return A JSONObject containing search suggestions or null if the request fails.
     * @throws IOException If a network or other I/O error occurs.
     */
    @Throws(IOException::class)
    fun suggest(searchQuery: String): JSONObject? {
        val endpoint = "search/suggest/DK"
        val params = mapOf("q" to searchQuery)
        Log.d("SearchAPI", "Fetching search suggestions from endpoint: $endpoint with params: $params")
        return HTTPClient.get(endpoint, params)
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
        val endpoint = "search/v5/DK"
        val params = mapOf(
            "q" to searchQuery,
            "carouselSize" to size.toString(),
            "suggestionsActive" to suggestionsActive.toString(),
            "suggestionClicked" to suggestionClicked.toString(),
            "suggestionReverted" to suggestionReverted.toString()
        )
        Log.d("SearchAPI", "Performing search from endpoint: $endpoint with params: $params")
        return HTTPClient.get(endpoint, params)
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
        val endpoint = "search/v5/DK"
        val params = mapOf(
            "q" to searchQuery,
            "carouselSize" to size.toString(),
            "suggestionsActive" to suggestionsActive.toString(),
            "suggestionClicked" to suggestionClicked.toString(),
            "suggestionReverted" to suggestionReverted.toString()
        )
        Log.d("SearchAPI", "Performing async search from endpoint: $endpoint with params: $params")
        return HTTPClient.getAsync(endpoint, params)
    }

    // You can add more search-related methods here as needed
}
