package com.example.bitable_fe.feature.onboarding.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.R
import com.example.bitable_fe.core.data.model.Bank
import com.example.bitable_fe.core.ui.theme.CustomTypography
import com.example.bitable_fe.core.ui.viewmodel.UserPreferencesViewModel
import com.example.bitable_fe.core.ui.viewmodel.UserViewModel
import androidx.compose.ui.semantics.contentDescription
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInputScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    userPreferencesViewModel: UserPreferencesViewModel = hiltViewModel(),
    onNextClick: () -> Unit
) {
    val accountId by userPreferencesViewModel.userIdFlow.collectAsState(initial = -1L)

    val bankList = listOf(
        Bank("NH농협", R.drawable.nh),
        Bank("카카오뱅크", R.drawable.kakao),
        Bank("KB국민", R.drawable.kb),
        Bank("토스뱅크", R.drawable.toss),
        Bank("신한", R.drawable.sh)
    )

    var accountNumber by remember { mutableStateOf("") }
    var selectedBank by remember { mutableStateOf<Bank?>(null) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrowback),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(0.5f))
            Text(
                text = "계좌 정보 입력",
                style = CustomTypography.titleLarge.copy(fontSize = 32.sp, fontWeight = FontWeight.ExtraBold),
                color = Color.Black,
                modifier = Modifier
                    .semantics {
                        heading()
                        contentDescription = "계좌 정보 입력"
                    }
            )

            Text(
                text = "해당 계좌를 통해 KRW 입출금",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                fontSize = 20.sp,
                modifier = Modifier.semantics {
                    contentDescription = "해당 계좌를 통해 원화 입출금이 가능합니다"
                }
            )

            Spacer(Modifier.height(28.dp))

            // 계좌번호 입력
            OutlinedTextField(
                value = accountNumber,
                onValueChange = { accountNumber = it },
                placeholder = { Text("계좌번호 입력") },
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))

            )

            Spacer(Modifier.height(14.dp))

            // 은행 선택 버튼
            OutlinedButton(
                onClick = { showSheet = true },
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .semantics{
                        contentDescription = "은행 선택 버튼"
                    },
                border = BorderStroke(1.dp, Color.LightGray),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    text = selectedBank?.name ?: "은행 선택",
                    modifier = Modifier.weight(1f),
                    color = if (selectedBank == null) Color.Gray else Color.Black
                )
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
            }

            Spacer(Modifier.height(30.dp))
            Spacer(Modifier.weight(1f))
            // 다음 버튼
            Button(
                onClick = {
                    userViewModel.setDefaultBankAccount(accountId, accountNumber.toLong())
                    onNextClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .semantics{
                        contentDescription = "다음 버튼"
                    },
                enabled = accountNumber.isNotBlank() && selectedBank != null && accountId != -1L
            ) {
                Text("다음")
            }
        }
    }

    // ---------------------------
    // BottomSheet - 은행 선택 UI
    // ---------------------------
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column {
                bankList.forEach { bank ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedBank = bank
                                showSheet = false
                            }
                            .padding(20.dp)
                            .semantics{
                                contentDescription = bank.name
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = bank.iconRes),
                            contentDescription = "${bank.name} 아이콘",
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(Modifier.width(16.dp))
                        Text(bank.name, fontSize = 18.sp)
                    }
                }
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

