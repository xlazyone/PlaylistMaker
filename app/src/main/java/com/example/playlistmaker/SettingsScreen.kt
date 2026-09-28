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
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onAgreementClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Настройки",
                        color = MaterialTheme.colorScheme.onBackground, // Цвет текста из темы
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = MaterialTheme.colorScheme.onBackground // Цвет иконки из темы
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background // Цвет фона из темы
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
                    color = MaterialTheme.colorScheme.onBackground
                )
                Switch(
                    checked = isDarkTheme, // Берем значение из параметра
                    onCheckedChange = onThemeChange // Вызываем функцию при переключении
                )
            }

            // 2. Поделиться приложением
            SettingsItem(
                icon = Icons.Default.Share,
                text = "Поделиться приложением",
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        // Ссылка на приложение (можешь заменить на любую)
                        putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.ru/android-developer/")
                    }
                    // createChooser покажет системное окно выбора приложения
                    context.startActivity(Intent.createChooser(shareIntent, "Поделиться приложением"))
                }
            )

            // 3. Написать в поддержку
            SettingsItem(
                icon = Icons.Default.SupportAgent,
                text = "Написать в поддержку",
                onClick = {
                    val supportIntent = Intent(Intent.ACTION_SENDTO).apply {
                        // mailto: означает, что мы хотим отправить email
                        data = Uri.parse("mailto:")
                        // Адрес получателя (замени на любой учебный email)
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("student@yandex.ru"))
                        // Тема письма
                        putExtra(Intent.EXTRA_SUBJECT, "Сообщение разработчикам приложения Playlist Maker")
                        // Текст письма
                        putExtra(Intent.EXTRA_TEXT, "Привет, разработчикам!\n\nСпасибо за отличное приложение!")
                    }
                    // Проверяем, есть ли на устройстве почтовые клиенты, чтобы избежать вылета
                    try {
                        context.startActivity(supportIntent)
                    } catch (e: Exception) {
                        // Если почты нет, можно показать Toast (всплывающее сообщение)
                        // Toast.makeText(context, "Нет приложения для отправки почты", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            // 4. Пользовательское соглашение
            SettingsItem(
                icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                text = "Пользовательское соглашение",
                onClick = onAgreementClick
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
            color = MaterialTheme.colorScheme.onBackground
        )

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant // Серый цвет из темы
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(
        isDarkTheme = false,
        onThemeChange = {},
        onAgreementClick = {},
        onBackClick = {}
    )
}