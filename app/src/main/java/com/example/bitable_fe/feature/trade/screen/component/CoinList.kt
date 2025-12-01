package com.example.bitable_fe.feature.trade.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitable_fe.core.data.model.CoinItem

@Composable
fun CoinList(onItemClick: (String) -> Unit) {
    val items = listOf(
        CoinItem("엑스알피(리플)", "XRP/KRW", "2,921", "-0.77%", false),
        CoinItem("비트코인", "BTC/KRW", "126,962,000", "-0.77%", false),
        CoinItem("플루이드", "FLUID/KRW", "6,190", "0.77%", true),
        CoinItem("플루이드", "FLUID/KRW", "6,190", "0.77%", true)
    )

    LazyColumn {
        items(items) { item ->
            CoinRow(item, { onItemClick(item.pair) })
            HorizontalDivider()
        }
    }
}

@Composable
fun CoinRow(item: CoinItem, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(item.name, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
            Text(item.pair, color = Color.Gray, fontSize = 20.sp)
            Text("거래 대금: 999,999백만", color = Color.Gray, fontSize = 20.sp)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                item.price,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0052FF),
                fontSize = 24.sp
            )
            Text(
                item.changeRate,
                color = if (item.isPositive) Color(0xFFD73D4A) else Color(0xFF3181F4),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}
