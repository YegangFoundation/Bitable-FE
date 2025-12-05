package com.example.bitable_fe.feature.invest.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitable_fe.core.network.response.PortfolioSummary

@Composable
fun PortfolioSummaryCard(summary: PortfolioSummary?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF6F7F9), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SummaryRow("총 매수", summary?.totalBalanceKrw?.toInt().toString())
        SummaryRow("총 평가", summary?.totalBalanceKrw?.toInt().toString())
        SummaryRow("평가 손익", summary?.totalProfitLossKrw?.toInt().toString())
        summary?.totalProfitLossRate?.let {
            SummaryRow(
                "수익률",
                "${"%.2f".format(summary.totalProfitLossRate * 100)}%",
                isRate = true,
                isPositive = it >= 0
            )
        }
    }
}

@Composable
private fun SummaryRow(
    title: String,
    value: String,
    isRate: Boolean = false,
    isPositive: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, color = Color.Gray, fontSize = 16.sp)

        Text(
            text = value + if (isRate) if (isPositive) " ▲" else " ▼" else "",
            color = if (!isRate) Color.Black else if (isPositive) Color(0xFFD73D4A) else Color(0xFF3181F4),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
