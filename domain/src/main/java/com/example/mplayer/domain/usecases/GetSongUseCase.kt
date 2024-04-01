package com.example.mplayer.domain.usecases

import com.example.mplayer.domain.models.Song
import com.example.mplayer.domain.repositories.SongRepository

class GetSongUseCase(
    private val songRepository: SongRepository
)  {
    suspend operator fun invoke(songId: Long): Song? {
        return songRepository.getSongById(songId)
    }
}