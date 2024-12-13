package com.example.techtrackr.ui.product

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.techtrackr.ui.navigation.CommonNavigationLayout
import com.example.techtrackr.utils.BASE_URL

@Composable
fun ProductContent(productViewModel: ProductViewModel) {
    val isLoading by productViewModel.isLoading.collectAsState()
    val errorMessage by productViewModel.errorMessage.collectAsState()
    val productDetails by productViewModel.productDetailsState.collectAsState()
    val productListings by productViewModel.productListingsState.collectAsState()

    CommonNavigationLayout(title = productDetails?.product?.name ?: "Product") { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
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
                    // State to manage the number of visible offers
                    var visibleOffersCount by remember { mutableStateOf(3) }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Product Images
                        val images = productDetails?.product?.images.orEmpty()
                        if (images.isNotEmpty()) {
                            item {
                                Card(
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .horizontalScroll(rememberScrollState())
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        images.forEach { img ->
                                            val imageUrl = img.path
                                            if (!imageUrl.isNullOrEmpty()) {
                                                AsyncImage(
                                                    model = BASE_URL + imageUrl,
                                                    contentDescription = img.description,
                                                    modifier = Modifier
                                                        .size(200.dp)
                                                        .clip(MaterialTheme.shapes.medium)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Product Name & Article
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = productDetails?.product?.name ?: "No name",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                val description = productDetails?.product?.description
                                if (description != null) {
                                    Text(
                                        text = description,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                        // Sellers (Offers) Section below Images
                        val offers = productListings?.offers.orEmpty()
                        val merchantsMap = productListings?.merchants.orEmpty()
                        if (offers.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Sellers",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            items(offers.take(visibleOffersCount)) { offer ->
                                val merchant = merchantsMap[offer.merchantId]
                                if (merchant != null) {
                                    OfferCard(
                                        offer = offer,
                                        merchant = merchant
                                    )
                                }
                            }

                            // Show "Show More" button if there are more offers to display
                            if (visibleOffersCount < offers.size) {
                                item {
                                    Button(
                                        onClick = {
                                            visibleOffersCount = (visibleOffersCount + 3).coerceAtMost(offers.size)
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                    ) {
                                        Text(text = "Show More")
                                    }
                                }
                            }
                        }

                        // Article (Collapsible)
                        val article = productDetails?.product?.article
                        if (!article.isNullOrEmpty()) {
                            item {
                                ExpandableSection(
                                    title = "Article",
                                    content = {
                                        Text(
                                            text = article,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                )
                            }
                        }

                        // Specifications Section (Collapsible)
                        val specs = productDetails?.specification?.sections.orEmpty()
                        if (specs.isNotEmpty()) {
                            item {
                                ExpandableSection(
                                    title = "Specifications",
                                    content = {
                                        specs.forEach { section ->
                                            Text(
                                                text = section.sectionName ?: "",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            section.attributes?.forEach { attr ->
                                                val values = attr.values?.joinToString { it.name ?: "" } ?: ""
                                                Text(
                                                    text = "- ${attr.name}: $values",
                                                    style = MaterialTheme.typography.bodySmall
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }
                                    }
                                )
                            }
                        }

                        // Reviews Section (Collapsible)
                        val review = productDetails?.productReviewSummary
                        if (review != null) {
                            item {
                                ExpandableSection(
                                    title = "Reviews",
                                    content = {
                                        Column {
                                            Text(
                                                text = "Average: ${review.average ?: "N/A"}",
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                text = "From ${review.count ?: 0} reviews",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            review.distribution?.let {
                                                Column {
                                                    RatingBar(ratingDistribution = it)
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                        }

                        // Additional Space at Bottom
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}
