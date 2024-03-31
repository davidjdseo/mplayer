package com.example.mplayer.domain.usecases

import com.example.mplayer.domain.models.Album
import com.example.mplayer.domain.repositories.AlbumRepository
import kotlinx.coroutines.flow.Flow

/**
 * 앨범 목록을 가져오는 유스케이스
 * @param albumRepository 앨범 리포지토리
 * @author david
 */
class GetAlbumsUseCase(
    private val albumRepository: AlbumRepository
) {
    /**
     * 앨범 목록을 Flow로 반환
     * @return 앨범 목록을 담고 있는 Flow
     */
    operator fun invoke(): Flow<List<Album>> {
        return albumRepository.getAlbums()
    }
}