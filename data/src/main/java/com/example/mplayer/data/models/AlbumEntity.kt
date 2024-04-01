package com.example.mplayer.data.models

import com.example.mplayer.domain.models.Album

/**
 * 앨범 정보를 담고 있는 데이터 클래스
 * @see Album
 * @author david
 */
data class AlbumEntity(
    val id: Long,
    val title: String,
    val artist: String,
    val coverArt: ByteArray?,
    val songs: List<SongEntity>
) {
    fun toDomainModel(): Album {
        return Album(
            id = id,
            title = title,
            artist = artist,
            coverArt = coverArt,
            songs = songs.map { it.toDomainModel() }
        )
    }
}