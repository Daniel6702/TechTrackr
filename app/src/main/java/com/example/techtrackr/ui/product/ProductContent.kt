package com.example.techtrackr.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import coil.compose.AsyncImage
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.techtrackr.data.model.*
import com.example.techtrackr.ui.navigation.CommonNavigationLayout
import com.example.techtrackr.utils.BASE_URL

@Composable
fun ProductContent(productViewModel: ProductViewModel) {
    val isLoading by productViewModel.isLoading.collectAsState()
    val errorMessage by productViewModel.errorMessage.collectAsState()
    val productDetails by productViewModel.productDetailsState.collectAsState()
    val productListings by productViewModel.productListingsState.collectAsState()

    CommonNavigationLayout(title = productDetails?.product?.name ?: "Product") { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                errorMessage != null -> {
                    Text(
                        text = errorMessage ?: "Unknown error",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        // Product images
                        val images = productDetails?.product?.images.orEmpty()
                        if (images.isNotEmpty()) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(rememberScrollState())
                                ) {
                                    images.forEach { img ->
                                        val imageUrl = img.path
                                        if (!imageUrl.isNullOrEmpty()) {
                                            AsyncImage(
                                                model = BASE_URL + imageUrl,
                                                contentDescription = img.description,
                                                modifier = Modifier
                                                    .size(200.dp)
                                                    .padding(end = 8.dp)
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }

                        // Product name & description
                        item {
                            Text(
                                text = productDetails?.product?.name ?: "No name",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            val desc = productDetails?.product?.description
                            if (!desc.isNullOrEmpty()) {
                                Text(text = desc, style = MaterialTheme.typography.bodyMedium)
                            }
                            val article = productDetails?.product?.article
                            if (!article.isNullOrEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = article, style = MaterialTheme.typography.bodySmall)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        // Specifications
                        val specs = productDetails?.specification?.sections.orEmpty()
                        if (specs.isNotEmpty()) {
                            item {
                                Text("Specifications", style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            items(specs) { section ->
                                if (section.attributes.isNullOrEmpty()) return@items
                                Text(section.sectionName ?: "", style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                section.attributes.forEach { attr ->
                                    val values = attr.values?.joinToString { it.name ?: "" } ?: ""
                                    Text("- ${attr.name}: $values", style = MaterialTheme.typography.bodySmall)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                                Divider()
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }

                        // Review summary
                        val review = productDetails?.productReviewSummary
                        if (review != null) {
                            item {
                                Text("Reviews", style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Average: ${review.average ?: "N/A"} from ${review.count ?: 0} reviews")
                                review.distribution?.let {
                                    Text("5 stars: ${it.five ?: 0}")
                                    Text("4 stars: ${it.four ?: 0}")
                                    Text("3 stars: ${it.three ?: 0}")
                                    Text("2 stars: ${it.two ?: 0}")
                                    Text("1 star: ${it.one ?: 0}")
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Divider()
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }

                        // Sellers (merchants) from productListings
                        val listings = productListings?.offers.orEmpty()
                            .groupBy { it.merchantId } // Group by merchantId (unique per seller)
                            .mapValues { (_, offers) ->
                                offers.minByOrNull { it.price?.amount?.toDoubleOrNull() ?: Double.MAX_VALUE } // Find lowest priced offer for each seller
                            }
                            .values
                            .filterNotNull() // Remove null values in case of invalid entries
                            .sortedBy { it.price?.amount?.toDoubleOrNull() ?: Double.MAX_VALUE } // Sort by price
                        val merchantsMap = productListings?.merchants.orEmpty()
                        if (listings.isNotEmpty()) {
                            item {
                                Text("Sellers", style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            items(listings) { offer ->
                                val merchant = merchantsMap[offer.merchantId]
                                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                    Text(
                                        text = offer.name ?: "No name",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    val price = offer.price?.amount + " " + offer.price?.currency
                                    Text(text = "Price: $price", style = MaterialTheme.typography.bodySmall)
                                    if (merchant != null) {
                                        Text(text = "Seller: ${merchant.name}", style = MaterialTheme.typography.bodySmall)
                                        merchant.rating?.average?.let { avg ->
                                            Text(text = "Merchant Rating: $avg", style = MaterialTheme.typography.bodySmall)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
