package com.example.playlistmaker

import androidx.annotation.DrawableRes

data class Track(
    val trackId: String,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    @DrawableRes val artworkResId: Int // Ссылка на картинку в папке res/drawable
)