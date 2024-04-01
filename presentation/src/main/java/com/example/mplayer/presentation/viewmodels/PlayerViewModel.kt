package com.example.mplayer.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mplayer.domain.models.Song
import com.example.mplayer.domain.repositories.AlbumRepository
import com.example.mplayer.domain.repositories.SongRepository
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 플레이어 화면의 ViewModel
 * @author david
 */
class PlayerViewModel(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository,
    application : Application
) : AndroidViewModel(application) {
    /**
     * 현재 재생 중인 곡의 ID를 저장하는 MutableStateFlow
     */
    private val _currentSongId = MutableStateFlow<Long>(-1L)

    /**
     * 현재 재생 중인 곡의 ID를 공개하는 StateFlow
     */
    val currentSongId: StateFlow<Long> = _currentSongId

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong

    /**
     * 새로운 곡을 재생하는 메서드
     * @param songId 재생할 곡의 ID
     */
    fun playNewSong(songId: Long) {
        viewModelScope.launch {
            val song = songRepository.getSongById(songId)
            _currentSongId.value = songId
            _currentSong.value = song
        }
    }

    private val _currentAlbumArt = MutableStateFlow<ByteArray?>(null)
    val currentAlbumArt: StateFlow<ByteArray?> = _currentAlbumArt

    fun playSong(songId: Long) {
        viewModelScope.launch {
            val song = songRepository.getSongById(songId)
            _currentSong.value = song
            song?.let {
                val album = albumRepository.getAlbumById(song.albumId)
                _currentAlbumArt.value = album?.coverArt

                val mediaItem = MediaItem.fromUri(it.fileName)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.play()
            }
        }
    }

    /**
     * 현재 재생중 여부를 판단하는 MutableStateFlow
     */
    private val _isPlaying = MutableStateFlow(false)

    /**
     * 현재 재생중 여부를 공개하는 StateFlow
     */
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    internal val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(getApplication()).build()
    }

    fun play() {
        _isPlaying.value = true
        exoPlayer.playWhenReady = true
    }

    fun pause() {
        _isPlaying.value = false
        exoPlayer.playWhenReady = false
    }

    fun skipToNext() {
        // TODO: 다음 곡으로 이동 로직 구현
        // exoPlayer.seekToNext()
    }

    fun skipToPrevious() {
        // TODO: 이전 곡으로 이동 로직 구현
        // exoPlayer.seekToPrevious()
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }


}