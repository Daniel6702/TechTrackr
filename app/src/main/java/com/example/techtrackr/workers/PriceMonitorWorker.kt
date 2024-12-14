package com.example.techtrackr.workers

// package com.example.techtrackr.workers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.techtrackr.R
import com.example.techtrackr.ui.watchlist.WatchlistProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

import com.example.techtrackr.data.remote.NetworkModule
import com.example.techtrackr.data.repository.ProductRepository

class PriceMonitorWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repository = ProductRepository(NetworkModule.apiService)

    override suspend fun doWork(): Result {
        Log.d("PriceMonitorWorker", "doWork() called")
        val currentUser = auth.currentUser ?: return Result.success()

        try {
            // Fetch watchlist
            val watchlistSnapshot = firestore.collection("users")
                .document(currentUser.uid)
                .collection("watchlist")
                .get()
                .await()

            val watchlistProducts = watchlistSnapshot.toObjects(WatchlistProduct::class.java)

            // Iterate through each watchlist product
            watchlistProducts.forEach { product ->
                val originalPrice = product.originalPrice
                if (originalPrice <= 0) return@forEach // Skip if original price is invalid

                // Fetch current listings
                val listingsResponse = try {
                    repository.getProductListings(product.productID)
                } catch (e: Exception) {
                    e.printStackTrace()
                    return@forEach
                }

                // Determine the cheapest offer
                val currentPrice = listingsResponse.offers
                    ?.mapNotNull { it.price?.amount?.toDoubleOrNull() }
                    ?.minOrNull() ?: return@forEach

                Log.d("PriceMonitorWorker", "Product: ${product.name}, Original Price: $originalPrice, Current Price: $currentPrice")

                if (currentPrice <= originalPrice * 0.9) {
                    // Price has dropped by 10% or more
                    sendNotification(product, originalPrice, currentPrice)

                    // Update current price to current price to prevent multiple notifications

                    firestore.collection("users")
                        .document(currentUser.uid)
                        .collection("watchlist")
                        .document(product.productID)
                        .update("currentPrice", currentPrice)
                        .addOnFailureListener { e -> e.printStackTrace() }

                }
            }

            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.retry()
        }
    }

    private fun sendNotification(product: WatchlistProduct, originalPrice: Double, currentPrice: Double) {
        Log.d("PriceMonitorWorker", "sendNotification() called with product: $product, originalPrice: $originalPrice, currentPrice: $currentPrice")
        val channelId = "price_drop_channel"
        val notificationId = product.productID.hashCode()

        // Create Notification Channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Price Drop Notifications"
            val descriptionText = "Notifications for price drops on watchlisted products"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Build Notification
        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_price_drop)
            .setContentTitle("Price Drop Alert!")
            .setContentText("${product.name} is now \$${"%.2f".format(currentPrice)}, down from \$${"%.2f".format(originalPrice)}.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Show Notification
        with(NotificationManagerCompat.from(applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId, builder.build())
        }
    }
}
