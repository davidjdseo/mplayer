package com.example.mplayer.domain.repositories

import com.example.mplayer.domain.models.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    fun getAlbums(): Flow<List<Album>>
}