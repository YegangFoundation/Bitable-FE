package com.example.bitable_fe.feature.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.R
import com.example.bitable_fe.core.network.response.UserResponse
import com.example.bitable_fe.core.ui.state.UserUiState
import com.example.bitable_fe.core.ui.viewmodel.UserPreferencesViewModel
import com.example.bitable_fe.core.ui.viewmodel.UserViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel(),
    userPref: UserPreferencesViewModel = hiltViewModel(),
    loginClicked: () -> Unit
) {
    val phoneState = rememberTextFieldState()
    val nameState = rememberTextFieldState()

    val uiState by userViewModel.state.collectAsState()

    // 회원가입 성공 시 화면 이동
    LaunchedEffect(uiState) {
        if (uiState is UserUiState.Success) {
            val res = (uiState as UserUiState.Success).data as UserResponse
            userPref.saveUserId(res.userId)
            loginClicked()
        }
    }

    // ⭐ 전화번호 자동 하이픈 포맷팅
    LaunchedEffect(phoneState) {
        snapshotFlow { phoneState.text.toString() }
            .collect { raw ->
                val digits = raw.filter { it.isDigit() }

                val formatted = when {
                    digits.length <= 3 -> digits
                    digits.length <= 7 -> digits.replace(Regex("(\\d{3})(\\d+)"), "$1-$2")
                    digits.length <= 11 -> digits.replace(Regex("(\\d{3})(\\d{4})(\\d+)"), "$1-$2-$3")
                    else -> digits.take(11).replace(Regex("(\\d{3})(\\d{4})(\\d+)"), "$1-$2-$3")
                }

                if (raw != formatted) {
                    phoneState.edit {
                        replace(0, length, formatted)
                    }
                }
            }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .semantics {
                contentDescription = "로그인 화면"
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "비트에이블 로고",
            modifier = Modifier
                .height(100.dp)
                .width(260.dp)
        )

        Spacer(Modifier.height(48.dp))

        // 전화번호 입력
        OutlinedTextField(
            state = phoneState,
            lineLimits = TextFieldLineLimits.SingleLine,
            placeholder = { Text("전화번호 입력") },
            modifier = Modifier
                .width(345.dp)
                .height(72.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, Color.Black)
                .semantics {
                    contentDescription = "전화번호 입력 필드"
                }
        )

        Spacer(Modifier.height(16.dp))

        // 이름 입력
        OutlinedTextField(
            state = nameState,
            lineLimits = TextFieldLineLimits.SingleLine,
            placeholder = { Text("이름 입력") },
            modifier = Modifier
                .width(345.dp)
                .height(72.dp)
                .clip(RoundedCornerShape(16.dp))
                .semantics {
                    contentDescription = "이름 입력 필드"
                }
        )

        Spacer(Modifier.height(48.dp))

        Button(
            onClick = {
                val phone = phoneState.text.toString()
                val name = nameState.text.toString()

                if (phone.isNotBlank() && name.isNotBlank()) {
                    userViewModel.loginOrCreateUser(phone, name)
                }
            },
            modifier = Modifier
                .width(345.dp)
                .height(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .semantics {
                    contentDescription = "시작 버튼"
                    role = Role.Button
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3181F4)
            )
        ) {
            Text(
                text = "시작",
                fontSize = 24.sp
            )
        }
    }
}


@Preview
@Composable
private fun LoginScreenPreview(){
    LoginScreen{}
}