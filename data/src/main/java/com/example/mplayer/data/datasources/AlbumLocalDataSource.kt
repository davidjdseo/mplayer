package com.example.mplayer.data.datasources

import android.content.Context
import android.provider.MediaStore
import com.example.mplayer.data.models.AlbumEntity
import com.example.mplayer.data.models.SongEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * 로컬 저장소에서 앨범 데이터를 가져오는 데이터 소스
 * @param context 애플리케이션 컨텍스트
 * @author david
 */
class AlbumLocalDataSource(private val context: Context) {

    /**
     * 모든 앨범 정보를 Flow로 반환.
     * @return 앨범 리스트를 담고 있는 Flow
     */
    fun getAlbums(): Flow<List<AlbumEntity>> = flow {
        val albums = mutableListOf<AlbumEntity>()
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )
        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST)
            val artColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val art = cursor.getString(artColumn)
                val songs = getSongs(id).first()

                albums += AlbumEntity(
                    id = id,
                    title = title,
                    artist = artist,
                    coverArt = art,
                    songs = songs
                )
            }
        }
        emit(albums)
    }.flowOn(Dispatchers.IO)

    private fun getSongs(albumId: Long): Flow<List<SongEntity>> {
        return SongLocalDataSource(context).getSongs(albumId)
    }
}