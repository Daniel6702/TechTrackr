package com.example.techtrackr.ui.product

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.draw.clip
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.techtrackr.data.model.ListingMerchant
import com.example.techtrackr.data.model.ListingOffer
import com.example.techtrackr.utils.BASE_URL

@Composable
fun OfferCard(
    offer: ListingOffer,
    merchant: ListingMerchant
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Merchant Image
            merchant.logo?.path?.let { logoPath ->
                AsyncImage(
                    model = BASE_URL + logoPath,
                    contentDescription = "${merchant.name} Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Offer Details
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                // Merchant Name
                Text(
                    text = merchant.name ?: "Unknown Seller",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                // Merchant Rating
                merchant.rating?.average?.let { avg ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = avg,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Product Name (Clickable)
                offer.name?.let { productName ->
                    Text(
                        text = productName,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clickable {
                                offer.url?.let { url ->
                                    val fullUrl = BASE_URL + url.substring(1)
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fullUrl))
                                    context.startActivity(intent)
                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Price
                offer.price?.let { price ->
                    Text(
                        text = "Price: ${price.amount} ${price.currency}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Delivery Time
                offer.deliveryTime?.let { delivery ->
                    val deliveryText = when {
                        delivery.minDays != null && delivery.maxDays != null -> {
                            "Delivery: ${delivery.minDays} - ${delivery.maxDays} days"
                        }
                        delivery.minDays != null -> {
                            "Delivery: ${delivery.minDays} days"
                        }
                        else -> {
                            "Delivery time not available"
                        }
                    }
                    Text(
                        text = deliveryText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Shipping Cost (Optional)
                offer.shippingCost?.let { shipping ->
                    Text(
                        text = "Shipping: ${shipping.amount} ${shipping.currency}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
