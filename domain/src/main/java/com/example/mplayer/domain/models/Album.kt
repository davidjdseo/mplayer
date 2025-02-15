package com.example.mplayer.domain.models

data class Album(
    val id: Long,
    val title: String,
    val artist: String,
    val coverArt: ByteArray?,
    val songs: List<Song>
)