package com.example.bitable_fe.feature.invest.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitable_fe.core.ui.viewmodel.PortfolioViewModel
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.ui.viewmodel.UserPreferencesViewModel

@Composable
fun PortfolioScreen(
    vm: PortfolioViewModel = hiltViewModel(),
    preferencesViewModel: UserPreferencesViewModel = hiltViewModel(),
    onClickDeposit: () -> Unit,
    onClickDetail: (String) -> Unit
) {
    val accountId by preferencesViewModel.userIdFlow.collectAsState(initial = null)
    val summary = vm.uiState.collectAsState().value
    val holdings = vm.holdings.collectAsState().value

    LaunchedEffect(Unit) {
        vm.loadAll(accountId = accountId!!)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔵 총 보유 자산
        Text("총 보유 자산", fontSize = 18.sp, color = Color.Gray)
        Spacer(Modifier.height(4.dp))
        Text(
            text = String.format("%,d", summary?.totalBalance?.toInt() ?: 0),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        // 🔵 입금 버튼
        Button(
            onClick = onClickDeposit,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2F8BFF)
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("입금", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))
        Divider(color = Color(0xFFE5E5E5), thickness = 8.dp)

        // 🔵 원화 표시
        AssetRow(
            title = "원화",
            symbol = "KRW",
            amount = 0.0,
            onClick = { onClickDetail("KRW") }
        )

        // 🔵 코인들 표시 (Holdings)
        holdings.forEach { h ->
            AssetRow(
                title = h.coinName,
                symbol = h.symbol,
                amount = h.quantity,
                krwValue = h.currentPrice * h.quantity,
                onClick = { onClickDetail(h.symbol) }
            )
        }
    }
}


@Composable
fun AssetRow(
    title: String,
    symbol: String,
    amount: Double,
    krwValue: Double = 0.0,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(symbol, fontSize = 14.sp, color = Color.Gray)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = amount.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = String.format("%,d KRW", krwValue.toInt()),
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }

    Divider(color = Color(0xFFE5E5E5))
}
