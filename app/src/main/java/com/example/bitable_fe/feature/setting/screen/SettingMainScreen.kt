package com.example.bitable_fe.feature.setting.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bitable_fe.R
import com.example.bitable_fe.core.ui.viewmodel.SettingsViewModel


@Composable
fun SettingsMainScreen(
    vm: SettingsViewModel = hiltViewModel(),
    onGoSpeed: () -> Unit,
    onGoInfoLevel: () -> Unit,
    onGoCustomize: () -> Unit,
    onGoCommandExample: () -> Unit,
    onResetClick: () -> Unit,
) {
    val userState = vm.users.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 13.dp)
        ) {
            Text("설정", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(24.dp))

        Spacer(Modifier.height(8.dp))

        /** -------------------------
         *  프로필 카드
         * ------------------------- */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(20.dp),
                    clip = false
                )
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = "User Icon",
                    modifier = Modifier.size(64.dp)
                )

                Spacer(Modifier.width(16.dp))

                Column {
                    Text(
                        text = userState.value?.name ?: "Name",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Email@gmail.com",
                        fontSize = 20.sp,
                        color = Color(0xFF6B7583)
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))


        /** -------------------------
         *  메뉴 리스트
         * ------------------------- */

        SettingsItem("음성 속도 설정", onGoSpeed)
        Divider(color = Color(0xFFE5E5E5))

        SettingsItem("음성 정보 단계", onGoInfoLevel)
        Divider(color = Color(0xFFE5E5E5))

        SettingsItem("읽기 항목 커스터마이즈", onGoCustomize)
        Divider(color = Color(0xFFE5E5E5))

        SettingsItem("음성 명령 예시", onGoCommandExample)
        Divider(color = Color(0xFFE5E5E5), thickness = 16.dp)

        Spacer(Modifier.height(24.dp))

        /** -------------------------
         *  Reset 버튼
         * ------------------------- */
        Text(
            text = "개인정보 및 데이터 리셋",
            color = Color.Red,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable(onClick = onResetClick)
                .padding(vertical = 12.dp)
        )
    }
}


@Composable
private fun SettingsItem(
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 18.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}


/** -------------------------
 *   PREVIEW — ViewModel 제거
 * ------------------------- */
@Preview(showBackground = true)
@Composable
fun SettingsMainScreenPreview() {
    SettingsMainScreen(
        onGoSpeed = {},
        onGoInfoLevel = {},
        onGoCustomize = {},
        onGoCommandExample = {},
        onResetClick = {}
    )
}
