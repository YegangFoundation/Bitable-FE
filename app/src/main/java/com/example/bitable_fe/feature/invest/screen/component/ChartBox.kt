package com.example.bitable_fe.feature.invest.screen.component

import android.media.AudioManager
import android.media.ToneGenerator
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun ChartBox(values: List<Double>) {
    val lineColor = Color(0xFF3A63F8)
    val highlightBorder = Color(0xFF3A63F8).copy(alpha = 0.4f)

    // ⭐ highlightIndex는 최소 0 이어야 함
    var highlightIndex by remember { mutableStateOf(0) }

    // ToneGenerator
    val toneGenerator = remember { ToneGenerator(AudioManager.STREAM_MUSIC, 100) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White)
            .pointerInput(values) {
                detectTapGestures { tapOffset ->
                    if (values.size < 2) return@detectTapGestures   // 안전 처리

                    val chartWidth = size.width
                    val step = chartWidth / (values.size - 1)

                    // X → index 변환
                    val index = ((tapOffset.x / step).toInt())
                        .coerceIn(0, values.size - 1)

                    highlightIndex = index

                    // 음계 계산
                    val v = values[index]
                    val max = values.maxOrNull() ?: v
                    val min = values.minOrNull() ?: v

                    if (max != min) {
                        val freqRatio = ((v - min) / (max - min)).coerceIn(0.0, 1.0)
                        val toneType = when (freqRatio) {
                            in 0.0..0.33 -> ToneGenerator.TONE_DTMF_1
                            in 0.33..0.66 -> ToneGenerator.TONE_DTMF_5
                            else -> ToneGenerator.TONE_DTMF_9
                        }
                        toneGenerator.startTone(toneType, 150)
                    }
                }
            }
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            if (values.size < 2) return@Canvas  // ⭐ 한 개 이하 데이터면 그리지 않음

            val maxValue = values.maxOrNull()!!
            val minValue = values.minOrNull()!!

            val chartHeight = size.height
            val chartWidth = size.width
            val step = chartWidth / (values.size - 1)

            // Y 좌표 변환
            fun mapY(v: Double): Float {
                return chartHeight -
                        (((v - minValue) / (maxValue - minValue)) * chartHeight).toFloat()
            }

            // ----- 스무스 라인 -----
            val smoothPath = Path()
            var x = 0f
            var prevX = 0f
            var prevY = mapY(values.first())
            smoothPath.moveTo(prevX, prevY)

            for (i in 1 until values.size) {
                x += step
                val y = mapY(values[i])
                val midX = (prevX + x) / 2f
                val midY = (prevY + y) / 2f
                smoothPath.quadraticTo(prevX, prevY, midX, midY)
                prevX = x
                prevY = y
            }
            smoothPath.lineTo(prevX, prevY)

            drawPath(
                path = smoothPath,
                color = lineColor,
                style = Stroke(width = 6f, cap = StrokeCap.Round)
            )

            // ⭐ highlightIndex 항상 안전한 범위로 보정
            val safeIndex = highlightIndex.coerceIn(0, values.size - 1)

            // ----- 하이라이트 박스 -----
            val highlightX = (safeIndex * step) - 40f
            val boxX = highlightX.coerceIn(0f, chartWidth - 80f)

            drawRoundRect(
                color = highlightBorder.copy(alpha = 0.15f),
                topLeft = Offset(boxX, 20f),
                cornerRadius = CornerRadius(20f, 20f),
                style = Stroke(width = 4f)
            )

            // ----- 선택된 점 강조 -----
            val selectedX = safeIndex * step
            val selectedY = mapY(values[safeIndex])

            drawCircle(
                color = lineColor,
                radius = 10f,
                center = Offset(selectedX, selectedY)
            )
        }
    }
}
