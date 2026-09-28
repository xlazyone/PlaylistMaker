package com.example.playlistmaker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Настройки",
                        color = Color.Black,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // 1. Темная тема (Switch)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Темная тема",
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Switch(
                    checked = false, // Пока выключено
                    onCheckedChange = { /* TODO: Логика переключения */ }
                )
            }

            // 2. Поделиться приложением
            SettingsItem(
                icon = Icons.Default.Share,
                text = "Поделиться приложением",
                onClick = { /* TODO: Логика шаринга */ }
            )

            // 3. Написать в поддержку
            SettingsItem(
                icon = Icons.Default.SupportAgent,
                text = "Написать в поддержку",
                onClick = { /* TODO: Логика письма */ }
            )

            // 4. Пользовательское соглашение
            SettingsItem(
                icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                text = "Пользовательское соглашение",
                onClick = { /* TODO: Переход на экран соглашения */ }
            )
        }
    }
}


@Composable
fun SettingsItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically // Выравнивание по центру по вертикали
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = Color.Black
        )

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Gray // Иконки в настройках обычно серые
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(onBackClick = {})
}