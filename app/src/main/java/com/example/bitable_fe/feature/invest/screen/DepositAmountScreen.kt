package com.example.bitable_fe.feature.invest.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.core.ui.viewmodel.DepositViewModel
import com.example.bitable_fe.feature.trade.screen.component.TradeNumberPad
import com.example.bitable_fe.feature.trade.screen.component.VoiceFloatingButton

@Composable
fun DepositAmountScreen(
    vm: DepositViewModel = hiltViewModel(),
    onDeposit: () -> Unit
) {
    val account by vm.account.collectAsState()
    val amount by vm.amount.collectAsState()

    Scaffold(
        floatingActionButton = { VoiceFloatingButton() }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text("계좌", fontSize = 20.sp, color = Color.Gray)
            Text(
                "${account?.accountName} ${account?.accountId}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Text("금액", fontSize = 18.sp, color = Color.Gray)
            Text("${amount} KRW", fontSize = 32.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(24.dp))

            TradeNumberPad { key ->
                vm.onKeyClicked(key)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { vm.requestDeposit(onDeposit) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF006AFF))
            ) {
                Text("입금", fontSize = 20.sp, color = Color.White)
            }
        }
    }
}
