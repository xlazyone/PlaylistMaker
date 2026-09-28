package com.example.playlistmaker

object MockData {
    val tracks = listOf(
        Track(
            trackId = "1",
            trackName = "Yesterday (Remastered 2009)",
            artistName = "The Beatles",
            trackTime = "5:35",
            artworkResId = R.drawable.cover_beatles_yesterday
        ),
        Track(
            trackId = "2",
            trackName = "Here Comes The Sun (Remastered)",
            artistName = "The Beatles",
            trackTime = "4:01",
            artworkResId = R.drawable.cover_beatles_here_comes_the_sun
        ),
        Track(
            trackId = "3",
            trackName = "No Reply",
            artistName = "The Beatles",
            trackTime = "5:12",
            artworkResId = R.drawable.cover_beatles_no_reply
        )
    )
}