package com.example.playlistmaker

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailsScreen(
    playlist: Playlist,
    onBackClick: () -> Unit,
    onTrackClick: (Track) -> Unit // ДОБАВИЛИ новый параметр
) {
    // Находим реальные треки по их ID
    val tracks = playlist.trackIds.mapNotNull { id ->
        MockData.tracks.find { it.trackId == id }
    }

    // Считаем общее время в секундах
    val totalSeconds = tracks.sumOf { track ->
        // Разбиваем строку "5:35" по символу ":"
        val parts = track.trackTime.split(":")
        val minutes = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val seconds = parts.getOrNull(1)?.toIntOrNull() ?: 0
        minutes * 60 + seconds
    }

    // Переводим секунды в минуты (округление вверх)
    val totalMinutes = if (totalSeconds > 0) {
        (totalSeconds + 59) / 60 // Округляем вверх
    } else {
        0
    }

    val descriptionText = if (tracks.isEmpty()) {
        "Нет треков"
    } else {
        "$totalMinutes минут • ${tracks.size} треков"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Плейлист",
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
                actions = {
                    IconButton(onClick = { /* TODO: Меню плейлиста */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Меню",
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
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Шапка плейлиста
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Обложка плейлиста
                    if (playlist.coverResId != null) {
                        Image(
                            painter = painterResource(id = playlist.coverResId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = playlist.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    if (!playlist.description.isNullOrBlank()) {
                        Text(
                            text = playlist.description,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = descriptionText, // Динамическое описание
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Список треков
            if (tracks.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "В этом плейлисте пока нет треков",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(tracks) { track ->
                    TrackItem(track = track) {
                        onTrackClick(track)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaylistDetailsScreenPreview() {
    MaterialTheme {
        PlaylistDetailsScreen(
            playlist = MockData.initialPlaylists[0],
            onBackClick = {},
            onTrackClick = {} // Заглушка
        )
    }
}