package com.example.techtrackr.data.remote.transport

import android.util.Log
import com.example.techtrackr.utils.BASE_API_URL
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * The HTTPClient singleton is a reusable HTTP client designed for API calls with built-in retry functionality and error handling.
 * It manages HTTP interactions more reliably by handling network errors, retrying failed requests, and logging important events.
 */
object HTTPClient {

    private const val BASE_URL = BASE_API_URL // Replace with your actual base URL
    private const val TIMEOUT_SECONDS: Long = 10
    private const val RETRIES: Int = 3
    private const val BACKOFF_FACTOR_MS: Long = 300
    private val STATUS_FOR_RETRY = listOf(429, 500, 502, 503, 504)

    private val client: OkHttpClient

    init {
        // Logging interceptor for debugging purposes
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("HTTPClient", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.HEADERS // Change from Level.BODY
        }
        // Retry interceptor with exponential backoff
        val retryInterceptor = Interceptor { chain ->
            var attempt = 0
            var request = chain.request()
            var response: Response? = null
            var exception: IOException? = null

            while (attempt < RETRIES) {
                try {
                    response = chain.proceed(request)
                    if (!STATUS_FOR_RETRY.contains(response.code)) {
                        return@Interceptor response
                    }
                } catch (e: IOException) {
                    exception = e
                }

                attempt++
                val backoff = BACKOFF_FACTOR_MS * (1 shl (attempt - 1)) // Exponential backoff
                Log.w("HTTPClient", "Request failed - Attempt $attempt/$RETRIES. Retrying in ${backoff}ms.")
                try {
                    Thread.sleep(backoff)
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    throw e
                }
            }

            // After retries, either return the last response or throw the last exception
            response ?: throw exception ?: IOException("Unknown network error")
        }

        // Build OkHttpClient with interceptors and timeout settings
        client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(retryInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Performs a synchronous GET request.
     *
     * @param endpoint API endpoint.
     * @param params Query parameters.
     * @param headers Request headers.
     * @return JSON response as a JSONObject, or null if failed.
     * @throws IOException If a network error occurs.
     */
    @Throws(IOException::class)
    fun get(
        endpoint: String,
        params: Map<String, String>? = null,
        headers: Map<String, String>? = null
    ): JSONObject? {
        // Build the full URL
        val urlBuilder = (BASE_URL + endpoint).toHttpUrlOrNull()?.newBuilder()
            ?: throw IllegalArgumentException("Invalid base URL or endpoint")

        // Add query parameters
        params?.forEach { (key, value) ->
            urlBuilder.addQueryParameter(key, value)
        }

        val url = urlBuilder.build()

        // Default headers
        val defaultHeaders = mapOf(
            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/58.0.3029.110 Safari/537.36",
            "Accept" to "application/json"
        )

        // Merge headers
        val finalHeaders = headers?.let { defaultHeaders + it } ?: defaultHeaders

        // Build the request
        val requestBuilder = Request.Builder()
            .url(url)
            .get()

        finalHeaders.forEach { (key, value) ->
            requestBuilder.addHeader(key, value)
        }

        val request = requestBuilder.build()

        Log.d("HTTPClient", "GET Request to URL: $url with headers: $finalHeaders")

        // Execute the request
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("HTTPClient", "GET ${response.request.url} failed with status ${response.code}")
                throw IOException("Unexpected code $response")
            }

            val responseBody = response.body?.string()
            Log.i("HTTPClient", "GET ${response.request.url} succeeded with status ${response.code}")

            val test = responseBody?.let {
                try {
                    JSONObject(it)
                } catch (e: Exception) {
                    Log.e("HTTPClient", "Failed to parse JSON response: ${e.message}")
                    null
                }
            }
            Log.i("HTTPClient", "Response body: $responseBody")
            return test
        }
    }

    /**
     * Performs an asynchronous GET request using Kotlin coroutines.
     *
     * @param endpoint API endpoint.
     * @param params Query parameters.
     * @param headers Request headers.
     * @return JSON response as a JSONObject, or null if failed.
     * @throws IOException If a network error occurs.
     */
    suspend fun getAsync(
        endpoint: String,
        params: Map<String, String>? = null,
        headers: Map<String, String>? = null
    ): JSONObject? {
        return kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            get(endpoint, params, headers)
        }
    }
}
