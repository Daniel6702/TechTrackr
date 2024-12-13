package com.example.techtrackr.ui.product

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.techtrackr.data.model.ReviewDistribution

@Composable
fun RatingBar(ratingDistribution: ReviewDistribution) {
    Column {
        listOf(
            5 to ratingDistribution.five,
            4 to ratingDistribution.four,
            3 to ratingDistribution.three,
            2 to ratingDistribution.two,
            1 to ratingDistribution.one
        ).forEach { (star, count) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$star stars",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.width(60.dp)
                )
                LinearProgressIndicator(
                    progress = (count ?: 0) / 100f, // Adjust based on total reviews if needed
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${count ?: 0}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
