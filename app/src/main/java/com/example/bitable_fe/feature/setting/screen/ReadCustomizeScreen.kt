package com.example.bitable_fe.feature.setting.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomizeReadingScreen(
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 13.dp)
        ) {
            Text("읽기 항목 커스터마이즈", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))

        // 필요 UI 작성
    }
}


@Composable
private fun ToggleRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 18.sp)
        Switch(checked = checked, onCheckedChange = onChange)
    }
}

data class ReadOptions(
    val currentPrice: Boolean,
    val changeRate: Boolean,
    val highLow: Boolean,
    val volume: Boolean
) {
    companion object {
        fun fromType(type: String?): ReadOptions {
            return when (type) {
                "basic" -> ReadOptions(true, false, false, false)
                "extended" -> ReadOptions(true, true, true, true)
                else -> ReadOptions(true, true, true, false)
            }
        }
    }

    fun toType(): String {
        return when {
            currentPrice && changeRate && highLow && volume -> "extended"
            currentPrice && !changeRate && !highLow && !volume -> "basic"
            else -> "custom"
        }
    }
}
