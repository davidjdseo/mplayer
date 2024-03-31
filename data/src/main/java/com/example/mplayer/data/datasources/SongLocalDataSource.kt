package com.example.mplayer.data.datasources

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.example.mplayer.data.models.SongEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.flowOn

/**
 * 로컬 저장소에서 곡 데이터를 가져오는 데이터 소스
 * @param context 애플리케이션 컨텍스트
 * @author david
 */
class SongLocalDataSource(private val context: Context) {

    /**
     * 로컬 저장소에서 곡 데이터를 가져오는 데이터 소스
     * 특정 앨범에 속한 곡들의 정보를 Flow로 반환
     * @param albumId 앨범 ID
     * @return 곡 리스트를 담고 있는 Flow
     */
    fun getSongs(albumId: Long): Flow<List<SongEntity>> = flow {
        val songs = mutableListOf<SongEntity>()
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = "${MediaStore.Audio.Media.ALBUM_ID} = ?"
        val selectionArgs = arrayOf(albumId.toString())
        val sortOrder = "${MediaStore.Audio.Media.TRACK}"
        val cursor = contentResolver.query(
            uri,
            null,
            selection,
            selectionArgs,
            sortOrder
        )
        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val albumArtColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART)
            val trackColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TRACK)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val albumArt = cursor.getString(albumArtColumn)
                val trackNumber = cursor.getInt(trackColumn)
                val duration = cursor.getLong(durationColumn)
                val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)

                songs += SongEntity(
                    id = id,
                    title = title,
                    artist = artist,
                    albumId = albumId,
                    albumArt = albumArt,
                    trackNumber = trackNumber,
                    fileName = uri.toString(),
                    duration = duration
                )
            }
        }
        emit(songs)
    }.flowOn(Dispatchers.IO)

    /**
     * 주어진 songId에 해당하는 곡 정보를 가져옴
     * @param songId 가져올 곡의 ID
     * @return 곡 정보를 담은 SongEntity 객체, 해당 곡이 없으면 null 반환
     */
    suspend fun getSongById(songId: Long): SongEntity? {
        return withContext(Dispatchers.IO) {
            val contentResolver = context.contentResolver
            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val selection = "${MediaStore.Audio.Media._ID} = ?"
            val selectionArgs = arrayOf(songId.toString())
            val cursor = contentResolver.query(
                uri,
                null,
                selection,
                selectionArgs,
                null
            )
            cursor?.use {
                if (cursor.moveToFirst()) {
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                    val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                    val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                    val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                    val albumArtColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART)
                    val trackColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TRACK)
                    val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val albumId = cursor.getLong(albumIdColumn)
                    val albumArt = cursor.getString(albumArtColumn)
                    val trackNumber = cursor.getInt(trackColumn)
                    val duration = cursor.getLong(durationColumn)
                    val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)

                    SongEntity(
                        id = id,
                        title = title,
                        artist = artist,
                        albumId = albumId,
                        albumArt = albumArt,
                        trackNumber = trackNumber,
                        fileName = uri.toString(),
                        duration = duration
                    )
                } else {
                    null
                }
            }
        }
    }
}