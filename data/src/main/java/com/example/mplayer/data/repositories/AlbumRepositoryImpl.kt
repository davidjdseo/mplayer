package com.example.mplayer.data.repositories

import com.example.mplayer.data.datasources.AlbumLocalDataSource
import com.example.mplayer.domain.models.Album
import com.example.mplayer.domain.repositories.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * AlbumRepository 인터페이스의 구현체
 * @see AlbumRepository
 * @author david
 */
class AlbumRepositoryImpl(
    private val localDataSource: AlbumLocalDataSource
) : AlbumRepository {

    /**
     * 모든 앨범 정보를 Flow로 반환.
     * @return 앨범 리스트를 담고 있는 Flow
     */
    override fun getAlbums(): Flow<List<Album>> {
        return localDataSource.getAlbums()
            .map { albums ->
                albums.map { it.toDomainModel() }
            }
    }
}