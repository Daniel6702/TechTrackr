package com.example.techtrackr.ui.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.techtrackr.data.model.CategoryProduct
import com.example.techtrackr.utils.BASE_URL

@Composable
fun CategoryProductCard(
    product: CategoryProduct,
    onClick: (CategoryProduct) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            .clickable { onClick(product) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            val imageUrl = product.image?.path
            if (imageUrl != null) {
                AsyncImage(
                    model = BASE_URL + imageUrl,
                    contentDescription = product.image.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(120.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            product.lowestPrice?.let {
                Text(
                    text = "${it.amount} ${it.currency}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
