package com.example.techtrackr.ui.product

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.techtrackr.data.model.ListingMerchant
import com.example.techtrackr.utils.BASE_URL

@Composable
fun SellerCard(merchant: ListingMerchant) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable {
                merchant.url?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Merchant Logo
            merchant.logo?.path?.let { logoPath ->
                AsyncImage(
                    model = BASE_URL + logoPath,
                    contentDescription = "${merchant.name} Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Merchant Name
            Text(
                text = merchant.name ?: "Unknown Seller",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 4.dp),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Merchant Rating
            merchant.rating?.average?.let { avg ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
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
        }
    }
}
