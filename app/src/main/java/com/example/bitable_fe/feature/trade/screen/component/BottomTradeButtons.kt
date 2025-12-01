package com.example.bitable_fe.feature.trade.screen.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BottomTradeButtons(
    onSellClick: () -> Unit,
    onBuyClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = onSellClick,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(Color(0xFF1565FF)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("매도")
        }

        Spacer(Modifier.width(12.dp))

        Button(
            onClick = onBuyClick,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(Color(0xFFDD1A1A)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("매수")
        }
    }
}
