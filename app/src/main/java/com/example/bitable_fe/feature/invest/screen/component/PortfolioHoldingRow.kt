package com.example.bitable_fe.feature.invest.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitable_fe.core.network.response.HoldingResponse

@Composable
fun PortfolioHoldingRow(item: HoldingResponse) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Text("${item.coinName}  ${item.symbol}", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "평가 손익",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "${item.profitLossKrw.toInt()} " +
                        if (item.profitLossRate >= 0) "▲" else "▼",
                color = if (item.profitLossRate >= 0) Color(0xFFD73D4A) else Color(0xFF3181F4),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text("수익률: ${"%.2f".format(item.profitLossRate)}%", fontSize = 14.sp)
        Text("보유수량: ${item.quantity}", fontSize = 14.sp)
        Text("평가금액: ${item.currentPrice * item.quantity}", fontSize = 14.sp)
        Text("매수금액: ${item.avgBuyPrice * item.quantity}", fontSize = 14.sp)
    }
}
