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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp


@Composable
fun ChartBox(values: List<Double>) {
    val lineColor = Color(0xFF3A63F8)
    val highlightBorder = Color(0xFF3A63F8).copy(alpha = 0.4f)

    // ⭐ 터치한 index
    var highlightIndex by remember { mutableStateOf(values.size - 1) }

    // ToneGenerator (사운드 출력)
    val toneGenerator = remember { ToneGenerator(AudioManager.STREAM_MUSIC, 100) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White)
            .pointerInput(values) {
                detectTapGestures { tapOffset ->
                    if (values.isEmpty()) return@detectTapGestures

                    val chartWidth = size.width
                    val step = chartWidth / (values.size - 1)

                    // X 좌표 → index 변환
                    val index = (tapOffset.x / step).toInt().coerceIn(0, values.size - 1)
                    highlightIndex = index

                    // 값 기반으로 음 높낮이 변경
                    val v = values[index]
                    val max = values.maxOrNull() ?: 1.0
                    val min = values.minOrNull() ?: 0.0

                    // 200Hz ~ 1000Hz 사이 비례 매핑
                    val freqRatio = ((v - min) / (max - min)).coerceIn(0.0, 1.0)
                    val toneType = when (freqRatio) {
                        in 0.0..0.33 -> ToneGenerator.TONE_DTMF_1  // 낮은 음
                        in 0.33..0.66 -> ToneGenerator.TONE_DTMF_5 // 중간 음
                        else -> ToneGenerator.TONE_DTMF_9          // 높은 음
                    }

                    toneGenerator.startTone(toneType, 150)
                }
            }
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            if (values.isEmpty()) return@Canvas

            val maxValue = values.maxOrNull() ?: 1.0
            val minValue = values.minOrNull() ?: 0.0

            val chartHeight = size.height
            val chartWidth = size.width
            val step = chartWidth / (values.size - 1)

            fun mapY(v: Double): Float {
                return chartHeight - ((v - minValue) / (maxValue - minValue) * chartHeight).toFloat()
            }

            // ----- 부드러운 라인 Path -----
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

            // ----- 라인 그리기 -----
            drawPath(
                path = smoothPath,
                color = lineColor,
                style = Stroke(width = 6f, cap = StrokeCap.Round)
            )

            // =========================
            // ⭐ 터치한 위치의 하이라이트 박스
            // =========================
            val highlightX = (highlightIndex * step) - 40f
            val boxX = highlightX.coerceIn(0f, chartWidth - 80f)

            drawRoundRect(
                color = highlightBorder.copy(alpha = 0.15f),
                topLeft = Offset(boxX, 20f),
                cornerRadius = CornerRadius(20f, 20f),
                style = Stroke(width = 4f)
            )

            // =========================
            // ⭐ 마지막 점 or 선택된 점 강조
            // =========================
            val selectedX = (highlightIndex * step)
            val selectedY = mapY(values[highlightIndex])

            drawCircle(
                color = lineColor,
                radius = 10f,
                center = Offset(selectedX, selectedY)
            )
        }
    }
}
