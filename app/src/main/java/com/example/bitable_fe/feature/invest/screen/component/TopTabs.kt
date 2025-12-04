package com.example.bitable_fe.feature.invest.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitable_fe.feature.invest.navgraph.InvestRoute

@Composable
fun InvestTopBar(
    selected: InvestRoute?,
    onTabSelected: (InvestRoute) -> Unit
) {
    Column {
        Spacer(Modifier.height(8.dp))

        Text(
            text = "투자정보",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InvestTabItem(
                text = "투자내역",
                selected = selected == InvestRoute.PortfolioRoute,
                onClick = { onTabSelected(InvestRoute.PortfolioRoute) }
            )
            InvestTabItem(
                text = "투자손익",
                selected = selected == InvestRoute.ProfitRoute,
                onClick = { onTabSelected(InvestRoute.ProfitRoute) }
            )
            InvestTabItem(
                text = "입출금",
                selected = selected == InvestRoute.DepositMainRoute,
                onClick = { onTabSelected(InvestRoute.DepositMainRoute) }
            )
        }

        androidx.compose.material3.Divider(color = Color(0xFFE0E0E0))
    }
}

@Composable
private fun InvestTabItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) Color(0xFF006AFF) else Color.Black
        )
        Spacer(Modifier.height(4.dp))
        if (selected) {
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .width(40.dp)
                    .background(Color(0xFF006AFF))
            )
        }
    }
}
