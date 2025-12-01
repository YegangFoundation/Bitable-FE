package com.example.bitable_fe.feature.trade.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitable_fe.core.data.model.Coin
import com.example.bitable_fe.feature.trade.screen.component.PercentSelector
import com.example.bitable_fe.feature.trade.screen.component.TradeInputCard
import com.example.bitable_fe.feature.trade.screen.component.TradeNumberPad

@Composable
fun SellScreen(coinName: String) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Mic, contentDescription = null)
            }
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            TradeInputCard(
                amount = "2 BTC",
                price = "126,556,000 KRW",
                total = "253,112,000 KRW"
            )

            Spacer(Modifier.height(16.dp))

            PercentSelector { /* percent action */ }

            Spacer(Modifier.height(16.dp))

            TradeNumberPad { /* key click */ }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF1565FF))
            ) {
                Text("매도", fontSize = 18.sp)
            }
        }
    }
}
