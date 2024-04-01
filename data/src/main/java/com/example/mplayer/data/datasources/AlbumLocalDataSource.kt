package com.example.mplayer.data.datasources

import android.content.Context
import android.media.MediaMetadataRetriever
import android.util.Log
import com.example.mplayer.data.models.AlbumEntity
import com.example.mplayer.data.models.SongEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * 로컬 저장소에서 앨범 데이터를 가져오는 데이터 소스
 * @param context 애플리케이션 컨텍스트
 * @author david
 */
class AlbumLocalDataSource(private val context: Context)  {

    /**
     * 모든 앨범 정보를 Flow로 반환.
     * @return 앨범 리스트를 담고 있는 Flow
     */
    fun getAlbums(): Flow<List<AlbumEntity>> = flow {
        Log.e("TAG", "getAlbums start")
        val albums = mutableListOf<AlbumEntity>()
        val assetManager = context.assets
        val musicFiles = assetManager.list("music") ?: emptyArray()

        musicFiles.forEach { fileName ->
            val mediaMetadataRetriever = MediaMetadataRetriever()
            val fileDescriptor = assetManager.openFd("music/$fileName")
            mediaMetadataRetriever.setDataSource(
                fileDescriptor.fileDescriptor,
                fileDescriptor.startOffset,
                fileDescriptor.length
            )

            val title = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            val artist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            val albumName = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
            val duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull() ?: 0L
            val albumArt = getAlbumArt(mediaMetadataRetriever) ?: getDefaultAlbumArt()

            val album = albums.find { it.title == albumName }
            if (album != null) {
                val songList = album.songs.toMutableList()
                Log.e("TAG", "getAlbums song title : $title length : ${albumArt?.size}")
                songList.add(
                    SongEntity(
                        id = 0,
                        title = title ?: "",
                        artist = artist ?: "",
                        albumId = album.id,
                        trackNumber = album.songs.size + 1,
                        fileName = "music/$fileName",
                        duration = duration
                    )
                )
                albums[albums.indexOf(album)] = album.copy(songs = songList)
            } else {
                Log.e("TAG", "getAlbums album title : $albumName")
                albums.add(
                    AlbumEntity(
                        id = albums.size.toLong(),
                        title = albumName ?: "",
                        artist = artist ?: "",
                        coverArt = albumArt,
                        songs = listOf(
                            SongEntity(
                                id = 0,
                                title = title ?: "",
                                artist = artist ?: "",
                                albumId = albums.size.toLong(),
                                trackNumber = 1,
                                fileName = "music/$fileName",
                                duration = duration
                            )
                        )
                    )
                )
            }
        }
        Log.e("TAG", "getAlbums album size : ${albums.size}")
        emit(albums)
    }.flowOn(Dispatchers.IO)

    suspend fun getAlbumById(albumId: Long): AlbumEntity? {
        return withContext(Dispatchers.IO) {
            val albums = getAlbums().first()
            albums.find { it.id == albumId }
        }
    }

    private fun getAlbumArt(mediaMetadataRetriever: MediaMetadataRetriever): ByteArray? {
        return mediaMetadataRetriever.embeddedPicture
    }

    private fun getDefaultAlbumArt(): ByteArray? {
        return null
    }


}