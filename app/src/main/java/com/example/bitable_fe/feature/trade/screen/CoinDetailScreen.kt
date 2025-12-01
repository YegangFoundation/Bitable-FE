package com.example.bitable_fe.feature.trade.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitable_fe.core.data.model.Coin
import com.example.bitable_fe.feature.trade.screen.component.BottomTradeButtons
import com.example.bitable_fe.feature.trade.screen.component.ChartPeriodTabs
import com.example.bitable_fe.feature.trade.screen.component.PriceInfoList
import com.example.bitable_fe.feature.trade.screen.component.VoiceFloatingButton

@Composable
fun CoinDetailScreen(
    coinName: String,
    onListenSummaryClick: () -> Unit = {},
    onSellClick: () -> Unit = {},
    onBuyClick: () -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            VoiceFloatingButton()
        },
        bottomBar = {
            BottomTradeButtons(
                onSellClick = onSellClick,
                onBuyClick = onBuyClick
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // ▣ 상단 제목
            item {
                Column {
                    Text("코인정보", fontSize = 14.sp, color = Color.Gray)
                    Text("엑스알피(리플)", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("XRP/KRW", fontSize = 16.sp, color = Color.Gray)
                }
            }

            // ▣ 기간 탭
            item {
                ChartPeriodTabs()
            }

            // ▣ 차트 (임시 UI 박스)
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color(0xFFEAF0FF), RoundedCornerShape(12.dp))
                )
            }

            // ▣ “차트 요약 듣기” 버튼
            item {
                Button(
                    onClick = onListenSummaryClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xFF006AFF)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("차트 요약 듣기", color = Color.White, fontSize = 18.sp)
                }
            }

            // ▣ 현재가 ~ 실시간 거래량 정보 리스트
            item {
                PriceInfoList()
            }

            // 하단 버튼 공간 확보
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}
