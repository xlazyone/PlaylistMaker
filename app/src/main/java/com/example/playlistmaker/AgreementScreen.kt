package com.example.playlistmaker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgreementScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Соглашение",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Добавляем возможность прокрутки
                .padding(16.dp)
        ) {
            // Текст из макета. Можешь заменить его на любой другой.
            Text(
                text = "Оферта на оказание образовательных услуг дополнительного образования Яндекс.Практикум для физических лиц\n\n" +
                        "Данный документ является действующим, если расположен по адресу: https://yandex.ru/legal/practicum_offer\n\n" +
                        "Российская Федерация, город Москва\n\n" +
                        "1. ТЕРМИНЫ\n\n" +
                        "Понятия, используемые в Оферте, означают следующее:\n\n" +
                        "Авторизованные адреса — адреса электронной почты каждой Стороны. Авторизованным адресом Исполнителя является адрес электронной почты, указанный в разделе 11 Оферты. Авторизованным адресом Студента является адрес электронной почты, указанный Студентом в Личном кабинете.\n\n" +
                        "Вводный курс — начальный Курс обучения по представленным на Сервисе Программам обучения в рамках выбранной Студентом Профессии или Курсу, рассчитанный на определенное количество часов самостоятельного обучения, который предоставляется Студенту единожды при регистрации на Сервисе на безвозмездной основе.\n\n" +
                        "2. ОБЩИЕ ПОЛОЖЕНИЯ\n\n" +
                        "2.1. Исполнитель оказывает услуги по обучению Студента на условиях, изложенных в настоящей Оферте.\n" +
                        "2.2. Акцепт Оферты производится путем регистрации на Сервисе.\n\n" +
                        "3. ПРАВА И ОБЯЗАННОСТИ СТОРОН\n\n" +
                        "3.1. Исполнитель обязуется предоставить доступ к образовательным материалам.\n" +
                        "3.2. Студент обязуется не передавать доступ третьим лицам.",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgreementScreenPreview() {
    AgreementScreen(onBackClick = {})
}