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
            // Состояние для списка плейлистов
            var playlists by remember { mutableStateOf(MockData.initialPlaylists) }
            // Состояние для избранных треков (храним их ID)
            var favoriteTrackIds by remember { mutableStateOf(setOf<String>()) }

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
                            onSearchClick = { navController.navigate("search") },
                            onPlaylistsClick = { navController.navigate("playlists") }, // <- ДОЛЖНО БЫТЬ
                            onFavoritesClick = { navController.navigate("favorites") }, // НОВОЕ
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
                        val track = MockData.tracks.find { it.trackId == trackId }

                        if (track != null) {
                            TrackDetailsScreen(
                                track = track,
                                isFavorite = favoriteTrackIds.contains(track.trackId),
                                playlists = playlists,
                                onBackClick = { navController.popBackStack() },
                                onFavoriteClick = {
                                    // Если трек уже в избранном — убираем, иначе — добавляем
                                    favoriteTrackIds = if (favoriteTrackIds.contains(track.trackId)) {
                                        favoriteTrackIds - track.trackId
                                    } else {
                                        favoriteTrackIds + track.trackId
                                    }
                                },
                                onPlaylistClick = { playlist ->
                                    // Добавляем ID трека в выбранный плейлист
                                    playlists = playlists.map { p ->
                                        if (p.id == playlist.id) {
                                            if (!p.trackIds.contains(track.trackId)) {
                                                p.copy(trackIds = p.trackIds + track.trackId)
                                            } else {
                                                p // Уже есть
                                            }
                                        } else {
                                            p
                                        }
                                    }
                                }
                            )
                        } else {
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
                    composable("playlists") {
                        PlaylistsScreen(
                            playlists = playlists,
                            onBackClick = { navController.popBackStack() },
                            onPlaylistClick = { playlist ->
                                navController.navigate("playlist_details/${playlist.id}")
                            },
                            onCreatePlaylistClick = { navController.navigate("create_playlist") }
                        )
                    }
                    composable("create_playlist") {
                        CreatePlaylistScreen(
                            onBackClick = { navController.popBackStack() },
                            onCreatePlaylist = { name, desc ->
                                val newPlaylist = Playlist(
                                    id = java.util.UUID.randomUUID().toString(),
                                    name = name,
                                    description = desc ?: "0 треков"
                                )
                                playlists = playlists + newPlaylist // Добавляем в список
                                navController.popBackStack() // Возвращаемся назад
                            }
                        )
                    }
                    composable("main") {
                        MainScreen(
                            onSearchClick = { navController.navigate("search") },
                            onPlaylistsClick = { navController.navigate("playlists") },
                            onFavoritesClick = { navController.navigate("favorites") },
                            onSettingsClick = { navController.navigate("settings") }
                        )
                    }
                    composable("playlist_details/{playlistId}") { backStackEntry ->
                        val playlistId = backStackEntry.arguments?.getString("playlistId")
                        val playlist = playlists.find { it.id == playlistId }
                        if (playlist != null) {
                            PlaylistDetailsScreen(
                                playlist = playlist,
                                onBackClick = { navController.popBackStack() },
                                onTrackClick = { track ->
                                    // Переходим на экран деталей трека (у нас уже есть такой маршрут!)
                                    navController.navigate("track_details/${track.trackId}")
                                }
                            )
                        } else {
                            LaunchedEffect(Unit) { navController.popBackStack() }
                        }
                    }
                    composable("favorites") {
                        // Превращаем ID в объекты Track
                        val favoriteTracks = MockData.tracks.filter { favoriteTrackIds.contains(it.trackId) }
                        FavoritesScreen(
                            favoriteTracks = favoriteTracks,
                            onBackClick = { navController.popBackStack() },
                            onTrackClick = { track ->
                                navController.navigate("track_details/${track.trackId}")
                            }
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
    onPlaylistsClick: () -> Unit,
    onFavoritesClick: () -> Unit, // НОВЫЙ ПАРАМЕТР
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
            MenuItem(icon = Icons.AutoMirrored.Filled.List, text = "Плейлисты") {
                onPlaylistsClick()
            }
            MenuItem(icon = Icons.Default.FavoriteBorder, text = "Избранное") {
                onFavoritesClick() // НОВОЕ
            }
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
            onPlaylistsClick = {},
            onFavoritesClick = {}, // Заглушка
            onSettingsClick = {}
        )
    }
}