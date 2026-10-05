package com.example.playlistmaker

object MockData {
    val tracks = listOf(
        // === The Beatles ===
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
        ),
        // === Linkin Park ===
        Track(
            trackId = "4",
            trackName = "Numb",
            artistName = "Linkin Park",
            trackTime = "3:07",
            artworkResId = R.drawable.cover_linkin_park_numb
        ),
        Track(
            trackId = "5",
            trackName = "In the End",
            artistName = "Linkin Park",
            trackTime = "3:36",
            artworkResId = R.drawable.cover_linkin_park_in_the_end
        ),
        Track(
            trackId = "6",
            trackName = "What I've Done",
            artistName = "Linkin Park",
            trackTime = "3:25",
            artworkResId = R.drawable.cover_linkin_park_what_ive_done
        ),
        // === SEREBRO ===
        Track(
            trackId = "7",
            trackName = "Отпусти меня",
            artistName = "SEREBRO",
            trackTime = "3:52",
            artworkResId = R.drawable.cover_serebro_otpusti_menya
        ),
        Track(
            trackId = "8",
            trackName = "СЛАДКО",
            artistName = "SEREBRO",
            trackTime = "3:59",
            artworkResId = R.drawable.cover_serebro_sladko
        ),
        Track(
            trackId = "9",
            trackName = "Мало тебя",
            artistName = "SEREBRO",
            trackTime = "3:45",
            artworkResId = R.drawable.cover_serebro_malo_tebya
        )
    )

    val initialPlaylists = listOf(
        Playlist(
            id = "1",
            name = "Best songs 2021",
            description = "Мои любимые песни",
            coverResId = R.drawable.cover_beatles_yesterday,
            trackIds = listOf("1", "2", "3")
        ),
        Playlist(
            id = "2",
            name = "Summer Party",
            description = "Для вечеринки",
            coverResId = R.drawable.cover_beatles_here_comes_the_sun,
            trackIds = listOf("2", "3")
        )
    )
}