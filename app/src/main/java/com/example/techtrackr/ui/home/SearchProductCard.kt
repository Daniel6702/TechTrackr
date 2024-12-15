package com.example.techtrackr.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.techtrackr.data.model.SearchProduct
import com.example.techtrackr.utils.BASE_URL

@Composable
fun SearchProductCard(
    searchProduct: SearchProduct,
    onClick: (SearchProduct) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(250.dp)
            .padding(8.dp)
            .clickable { onClick(searchProduct) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            val imageUrl = searchProduct.image?.path
            if (imageUrl != null) {
                AsyncImage(
                    model = BASE_URL + imageUrl,
                    contentDescription = searchProduct.image.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(120.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = searchProduct.name ?: "Unnamed Product",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            searchProduct.lowestPrice?.let { price ->
                Text(
                    text = "${price.amount} ${price.currency}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
