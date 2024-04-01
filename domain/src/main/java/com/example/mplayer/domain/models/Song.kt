package com.example.mplayer.domain.models

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val albumId: Long,
    val trackNumber: Int,
    val fileName: String,
    val duration: Long
)