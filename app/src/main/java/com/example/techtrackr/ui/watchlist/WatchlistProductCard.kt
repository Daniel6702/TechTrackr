package com.example.techtrackr.ui.watchlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.techtrackr.utils.BASE_URL

@Composable
fun WatchlistProductCard(
    product: WatchlistProduct,
    onRemoveClick: (String) -> Unit,
    onProductClick: (WatchlistProduct) -> Unit
) {
    val originalPrice = product.originalPrice
    val currentPrice = product.currentPrice

    // Determine price change
    val priceChange = when {
        currentPrice > originalPrice -> "higher"
        currentPrice < originalPrice -> "lower"
        else -> "same"
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick(product) } // Make the entire card clickable
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            // Product Image
            if (product.imageUrl != null) {
                val full_url = BASE_URL + product.imageUrl
                Image(
                    painter = rememberAsyncImagePainter(full_url),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                // Placeholder Image
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Image",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Product Details
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Price Row with Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DKK $currentPrice",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    when (priceChange) {
                        "higher" -> {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "Price Increased",
                                tint = Color.Red,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        "lower" -> {
                            Icon(
                                imageVector = Icons.Filled.ArrowDownward,
                                contentDescription = "Price Decreased",
                                tint = Color.Green,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        else -> {
                            // Optionally, you can display a neutral icon or nothing
                            Icon(
                                imageVector = Icons.Filled.Delete, // Placeholder icon
                                contentDescription = "Price Unchanged",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Original Price (optional)
                if (priceChange != "same") {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Original: DKK $originalPrice",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }

            // Remove Button
            IconButton(
                onClick = {
                    onRemoveClick(product.productID)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from Watchlist",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
