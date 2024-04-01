package com.example.mplayer.data.models

import com.example.mplayer.domain.models.Song

/**
 * 곡 정보를 담고 있는 데이터 클래스
 * @see Song
 * @author david
 */
data class SongEntity(
    val id: Long,
    val title: String,
    val artist: String,
    val albumId: Long,
    val trackNumber: Int,
    val fileName: String,
    val duration: Long
) {
    fun toDomainModel(): Song {
        return Song(
            id = id,
            title = title,
            artist = artist,
            albumId = albumId,
            trackNumber = trackNumber,
            fileName = fileName,
            duration = duration
        )
    }
}