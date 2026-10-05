package com.example.playlistmaker

import androidx.annotation.DrawableRes

data class Playlist(
    val id: String,
    val name: String,
    val description: String?, // Пользовательское описание (может быть пустым)
    @DrawableRes val coverResId: Int? = null,
    val trackIds: List<String> = emptyList() // Список ID треков
)