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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitable_fe.core.data.model.CoinItem
import com.example.bitable_fe.core.network.response.MarketData

@Composable
fun CoinList(
    items: List<MarketData>,
    onItemClick: (String) -> Unit
) {
    LazyColumn {
        items(items) { item ->
            CoinRow(item) { onItemClick(item.market) }
            HorizontalDivider()
        }
    }
}

@Composable
fun CoinRow(
    item: MarketData,
    onClick: () -> Unit
) {
    // market = "XRP-KRW" → name = XRP, pair = KRW
    val (symbol, currency) = item.market.split("-")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(item.koreanName, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text("${symbol}/${currency}", color = Color.Gray, fontSize = 20.sp)

            Text(
                "거래 대금: ${"%,.0f".format(item.acc_trade_price_24h)}원",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier
                    .semantics{
                        contentDescription = "거래 대금: ${"%,.0f".format(item.acc_trade_price_24h)}원"
                    }
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                "%,d".format(item.trade_price.toInt()),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0052FF),
                fontSize = 24.sp
            )

            val isPositive = item.change_rate >= 0
            Text(
                String.format("%.2f%%", item.change_rate * 100),
                fontWeight = FontWeight.Bold,
                color = if (isPositive) Color(0xFFD73D4A) else Color(0xFF3181F4),
                fontSize = 20.sp
            )
        }
    }
}

