package com.example.mplayer.data.repositories

import android.util.Log
import com.example.mplayer.data.datasources.SongLocalDataSource
import com.example.mplayer.domain.models.Song
import com.example.mplayer.domain.repositories.SongRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * SongRepository 인터페이스의 구현체 
 * @see SongRepository
 * @author david
 */
class SongRepositoryImpl(  
    private val localDataSource: SongLocalDataSource
) : SongRepository {

    /**
     * 특정 앨범에 속한 곡들의 정보를 Flow로 반환.
     * @param albumId 앨범 ID
     * @return 곡 리스트를 담고 있는 Flow
     */
    override fun getSongs(albumId: Long): Flow<List<Song>> {
        Log.e("TAG", "call getSongs ")
        return localDataSource.getSongs(albumId)
            .map { songs -> 
                songs.map {
                    Log.e("TAG", "getSongs song : ${it.toString()}")
                    it.toDomainModel()
                }
            }
    }

    override suspend fun getSongById(songId: Long): Song? {
        return localDataSource.getSongById(songId)?.toDomainModel()
    }
}