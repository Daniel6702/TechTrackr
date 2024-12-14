package com.example.techtrackr.ui.product

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp


@Composable
fun LineChart(
    data: List<Float>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        if (data.isEmpty()) return@Canvas

        val max = data.maxOrNull() ?: 1f
        val min = data.minOrNull() ?: 0f
        val range = max - min
        val width = size.width
        val height = size.height
        val pointGap = width / (data.size - 1).coerceAtLeast(1)

        val points = data.mapIndexed { index, value ->
            Offset(
                x = index * pointGap,
                y = height - ((value - min) / range) * height
            )
        }

        // Draw lines
        for (i in 0 until points.size - 1) {
            drawLine(
                color = Color.Blue,
                start = points[i],
                end = points[i + 1],
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Draw points
        points.forEach { point ->
            drawCircle(
                color = Color.Red,
                radius = 6.dp.toPx(),
                center = point
            )
        }
    }
}
