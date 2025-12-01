package com.example.bitable_fe.feature.trade.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PriceInfoList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        PriceRow("현재가", "126,962,000", "-0.34%")
        PriceRow("24h 변동률", "32%", "-0.34%")
        PriceRow("당일 고가", "129,000,000", "1.55%↑", isUp = true)
        PriceRow("당일 저가", "126,063,000", "-1.55%↓", isUp = false)
        PriceRow("실시간 거래량", "83,164.709 ETH", null)
    }
}

@Composable
private fun PriceRow(
    label: String,
    value: String,
    rate: String?,
    isUp: Boolean? = null
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(label, color = Color.Gray, fontSize = 15.sp)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp)

            rate?.let {
                val color = when (isUp) {
                    true -> Color(0xFFE53935)
                    false -> Color(0xFF2196F3)
                    else -> Color.Gray
                }
                Text(rate, color = color, fontSize = 14.sp)
            }
        }
    }
}
