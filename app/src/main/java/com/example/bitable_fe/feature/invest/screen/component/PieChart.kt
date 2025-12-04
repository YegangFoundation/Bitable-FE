package com.example.bitable_fe.feature.invest.screen.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun PieChart(
    data: List<Double>,
    labels: List<String>
) {
    if (data.isEmpty()) return

    val total = data.sum()
    val colors = listOf(
        Color(0xFF6A5ACD),
        Color(0xFF42A5F5),
        Color(0xFFFF7043),
        Color(0xFF66BB6A)
    )

    Canvas(modifier = Modifier.size(200.dp)) {

        var startAngle = 0f

        data.forEachIndexed { index, value ->
            val sweep = (value / total * 360f).toFloat()

            drawArc(
                color = colors[index % colors.size],
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = true,
                size = Size(size.width, size.height)
            )

            startAngle += sweep
        }
    }
}
