package com.example.playlistmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.content.Context
import androidx.compose.runtime.*
import com.example.playlistmaker.ui.theme.PlaylistMakerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Получаем доступ к хранилищу настроек
        val sharedPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        setContent {
            // 2. Создаем состояние темы. По умолчанию берем значение из настроек
            var isDarkTheme by remember {
                mutableStateOf(sharedPrefs.getBoolean("dark_theme", false))
            }

            // НОВОЕ: Создаем состояние истории на уровне всего приложения
            var searchHistory by remember { mutableStateOf(listOf<String>()) }
            // НОВОЕ: Создаем состояние поискового запроса тоже здесь, чтобы оно сохранялось
            var searchQuery by remember { mutableStateOf("") }

            // 3. Передаем наше состояние в тему
            // (В файле Theme.kt обычно есть функция PlaylistMakerTheme, которая принимает darkTheme)
            PlaylistMakerTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    composable("main") {
                        MainScreen(
                            onSearchClick = { navController.navigate("search") }, // Переход на поиск
                            onSettingsClick = { navController.navigate("settings") }
                        )
                    }
                    composable("search") {
                        SearchScreen(
                            searchQuery = searchQuery,           // Передаем запрос
                            onSearchQueryChange = { searchQuery = it }, // Колбэк для изменения
                            searchHistory = searchHistory,       // Передаем историю
                            onHistoryChange = { searchHistory = it }, // Колбэк для изменения
                            onBackClick = { navController.popBackStack() },
                            onTrackClick = { track ->
                                navController.navigate("track_details/${track.trackId}")
                            }
                        )
                    }
                    composable("track_details/{trackId}") { backStackEntry ->
                        val trackId = backStackEntry.arguments?.getString("trackId")
                        // Ищем трек в моках по ID
                        val track = MockData.tracks.find { it.trackId == trackId }

                        if (track != null) {
                            TrackDetailsScreen(
                                track = track,
                                onBackClick = { navController.popBackStack() }
                            )
                        } else {
                            // Если трек не найден (например, ошибка), просто возвращаемся назад
                            LaunchedEffect(Unit) { navController.popBackStack() }
                        }
                    }
                    composable("settings") {
                        SettingsScreen(
                            isDarkTheme = isDarkTheme,
                            onThemeChange = { newValue ->
                                isDarkTheme = newValue
                                sharedPrefs.edit().putBoolean("dark_theme", newValue).apply()
                            },
                            onAgreementClick = { navController.navigate("agreement") }, // Переход на соглашение
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                    composable("agreement") {
                        AgreementScreen(
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    // Scaffold - это каркас экрана
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Playlist Maker",
                        color = Color.White,
                        fontSize = 22.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3772E7) // Синий цвет из макета
                )
            )
        }
    ) { paddingValues ->
        // Column - это вертикальный список
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Передаем наши кнопки в список
            MenuItem(icon = Icons.Default.Search, text = "Поиск") {
                onSearchClick()
            }
            MenuItem(icon = Icons.AutoMirrored.Filled.List, text = "Плейлисты") { /* TODO */ }
            MenuItem(icon = Icons.Default.FavoriteBorder, text = "Избранное") { /* TODO */ }
            MenuItem(icon = Icons.Default.Settings, text = "Настройки") {
                onSettingsClick()
            }
        }
    }
}

// Создаем свою функцию для одной кнопки меню, чтобы не дублировать код 4 раза
@Composable
fun MenuItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Иконка слева
        Icon(
            imageVector = icon,
            contentDescription = null, // Описание для доступности (пока null)
            tint = MaterialTheme.colorScheme.onBackground
        )

        // Текст посередине. weight(1f) заставляет его занять всё свободное место
        Text(
            text = text,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Стрелочка справа
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen(
            onSearchClick = {},
            onSettingsClick = {}
        )
    }
}