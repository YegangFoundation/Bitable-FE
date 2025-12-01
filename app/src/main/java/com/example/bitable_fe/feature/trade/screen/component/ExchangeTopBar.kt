package com.example.bitable_fe.feature.trade.screen.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "거래소",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    )
}
