package com.example.mplayer.domain.repositories

import com.example.mplayer.domain.models.Song  
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    fun getSongs(albumId: Long): Flow<List<Song>>
    suspend fun getSongById(songId: Long): Song?
}