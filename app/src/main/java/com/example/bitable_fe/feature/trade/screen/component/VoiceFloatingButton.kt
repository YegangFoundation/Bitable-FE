package com.example.bitable_fe.feature.trade.screen.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun VoiceFloatingButton() {
    FloatingActionButton(
        onClick = {},
        containerColor = Color(0xFF006AFF),
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Mic,
            contentDescription = "Voice search",
            tint = Color.White
        )
    }
}
