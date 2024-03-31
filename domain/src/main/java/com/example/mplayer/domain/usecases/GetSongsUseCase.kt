package com.example.mplayer.domain.usecases

import com.example.mplayer.domain.models.Song
import com.example.mplayer.domain.repositories.SongRepository
import kotlinx.coroutines.flow.Flow

/**
 * 곡 목록을 가져오는 유스케이스
 * @param songRepository 곡 리포지토리
 * @author david
 */
class GetSongsUseCase(
    private val songRepository: SongRepository
) {
    /**
     * 특정 앨범의 곡 목록을 Flow로 반환
     * @param albumId 앨범 ID
     * @return 곡 목록을 담고 있는 Flow
     */
    operator fun invoke(albumId: Long): Flow<List<Song>> {
        return songRepository.getSongs(albumId)
    }
}