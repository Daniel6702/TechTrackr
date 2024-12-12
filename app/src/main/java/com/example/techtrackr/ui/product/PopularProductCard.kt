package com.example.techtrackr.ui.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.techtrackr.data.model.PopularProductItem
import com.example.techtrackr.utils.BASE_URL

@Composable
fun PopularProductCard(product: PopularProductItem) {
    Card(
        modifier = Modifier
            .size(width = 200.dp, height = 250.dp)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            coil.compose.AsyncImage(
                model = BASE_URL + product.image.path,
                contentDescription = product.image.description,
                modifier = Modifier.height(100.dp).fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.lowestPrice?.amount + " " + product.lowestPrice?.currency,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
