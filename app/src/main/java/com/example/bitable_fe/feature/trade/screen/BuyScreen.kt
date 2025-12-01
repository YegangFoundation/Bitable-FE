package com.example.bitable_fe.feature.trade.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.bitable_fe.feature.trade.screen.component.VoiceFloatingButton

@Composable
fun BuyScreen(coinName: String) {
    Scaffold(
        floatingActionButton = {
            VoiceFloatingButton()
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

            PercentSelector { }

            Spacer(Modifier.height(16.dp))

            TradeNumberPad { }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFFDD1A1A))
            ) {
                Text("매수", fontSize = 18.sp)
            }
        }
    }
}
