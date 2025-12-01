package com.example.bitable_fe.feature.trade.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TradeInputRow(
    label: String,
    value: String,
    unit: String = "",
    bold: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color(0xFFF2F3F5), RoundedCornerShape(10.dp)) // ← 내부 회색
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = label,
            fontSize = 24.sp,
            color = Color.Gray
        )

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = if (bold) FontWeight.Bold else FontWeight.Medium,
                color = if (bold) Color(0xFF1A1E27) else Color(0xFF333333)
            )

            if (unit.isNotEmpty()) {
                Spacer(Modifier.width(4.dp))
                Text(
                    text = unit,
                    fontSize = 24.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
