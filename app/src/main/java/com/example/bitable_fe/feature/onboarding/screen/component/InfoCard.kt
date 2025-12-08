package com.example.bitable_fe.feature.onboarding.screen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitable_fe.core.ui.theme.CustomTypography

@Composable
fun InfoCard(
    title: String,
    content: String,
    isSelected: Boolean, // 추가된 isSelected state
    onClick: () -> Unit // 추가된 클릭 이벤트 핸들러
) {
    // 선택 여부에 따라 테두리 색상과 굵기를 변경합니다.
    val borderColor = if (isSelected) Color(0xFF4285F4) else Color.LightGray // 선택되면 파란색, 아니면 연한 회색
    val borderWidth = if (isSelected) 2.dp else 1.dp // 선택되면 더 굵게

    Card(
        modifier = Modifier
            .fillMaxWidth()
            // clickable Modifier 추가 및 클릭 핸들러 연결
            .clickable(onClick = onClick)
            .semantics {
                contentDescription = content
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        // isSelected 값에 따라 테두리 설정
        border = BorderStroke(borderWidth, borderColor)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = title,
                style = CustomTypography.titleMedium.copy(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = content,
                style = CustomTypography.bodyMedium.copy(lineHeight = 22.sp),
                color = Color.Black,
                fontSize = 20.sp
            )
        }
    }
}