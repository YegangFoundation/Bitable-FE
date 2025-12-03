package com.example.bitable_fe.feature.invest.screen.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ChartBox(values: List<Double>) {
    val lineColor = Color(0xFF3A63F8)
    val highlightBorder = Color(0xFF3A63F8).copy(alpha = 0.4f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White)
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            if (values.isEmpty()) return@Canvas

            val maxValue = values.maxOrNull() ?: 1.0
            val minValue = values.minOrNull() ?: 0.0

            val chartHeight = size.height
            val chartWidth = size.width

            // 데이터 간 간격
            val step = chartWidth / (values.size - 1)

            // 좌표 변환
            fun mapY(v: Double): Float {
                return chartHeight - ((v - minValue) / (maxValue - minValue) * chartHeight).toFloat()
            }

            // ----------------------------
            // 1) 라인 Path
            // ----------------------------
            val path = Path()
            path.moveTo(0f, mapY(values.first()))

            var x = 0f
            for (i in 1 until values.size) {
                x += step
                val y = mapY(values[i])
                path.lineTo(x, y)
            }

            // 부드러운 라인 (Quadratic smoothing)
            val smoothPath = Path()
            val startX = 0f
            val startY = mapY(values.first())
            smoothPath.moveTo(startX, startY)

            var prevX = startX
            var prevY = startY

            x = 0f
            for (i in 1 until values.size) {
                x += step
                val y = mapY(values[i])
                val midX = (prevX + x) / 2f
                val midY = (prevY + y) / 2f

                smoothPath.quadraticTo(prevX, prevY, midX, midY)

                prevX = x
                prevY = y
            }

            // 마지막 점 처리
            smoothPath.lineTo(prevX, prevY)

            // ----------------------------
            // 2) 라인 그리기
            // ----------------------------
            drawPath(
                path = smoothPath,
                color = lineColor,
                style = Stroke(width = 6f, cap = StrokeCap.Round)
            )

            // ----------------------------
            // 3) 오른쪽 하이라이트 박스
            // ----------------------------
            val highlightWidth = chartWidth * 0.15f
            val highlightLeft = chartWidth - highlightWidth - 10f

            drawRoundRect(
                color = highlightBorder.copy(alpha = 0.15f),
                topLeft = Offset(highlightLeft, 20f),
                cornerRadius = CornerRadius(20f, 20f),
                style = Stroke(width = 4f)
            )

            // ----------------------------
            // 4) 마지막 점 강조
            // ----------------------------
            val lastX = prevX
            val lastY = prevY

            drawCircle(
                color = lineColor,
                radius = 10f,
                center = Offset(lastX, lastY)
            )
        }
    }
}
