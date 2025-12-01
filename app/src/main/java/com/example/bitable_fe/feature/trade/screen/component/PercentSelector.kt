package com.example.bitable_fe.feature.trade.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PercentSelector(
    percentages: List<String> = listOf("10%", "25%", "50%", "최대"),
    onSelect: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        percentages.forEach { text ->
            Box(
                modifier = Modifier.run {
                    clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF2F3F5))
                                .clickable { onSelect(text) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                },
            ) {
                Text(text, fontSize = 14.sp)
            }
        }
    }
}
