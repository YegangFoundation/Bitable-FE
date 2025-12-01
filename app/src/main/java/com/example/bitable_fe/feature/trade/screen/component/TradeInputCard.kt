package com.example.bitable_fe.feature.trade.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TradeInputCard(
    amount: String,
    price: String,
    total: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F8FA), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        TradeItemRow("수량", amount)
        Spacer(Modifier.height(8.dp))
        TradeItemRow("가격", price)
        Spacer(Modifier.height(8.dp))
        TradeItemRow("총액", total)
    }
}

@Composable
private fun TradeItemRow(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontWeight = FontWeight.Medium, fontSize = 16.sp)
        Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}
