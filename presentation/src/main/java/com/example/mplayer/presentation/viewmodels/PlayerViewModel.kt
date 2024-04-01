package com.example.mplayer.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mplayer.domain.models.Album
import com.example.mplayer.domain.models.Song
import com.example.mplayer.domain.repositories.AlbumRepository
import com.example.mplayer.domain.repositories.SongRepository
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.delay
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
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()
    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()
    private val _currentAlbumArt = MutableStateFlow<ByteArray?>(null)
    val currentAlbumArt: StateFlow<ByteArray?> = _currentAlbumArt
    private val _currentAlbum = MutableStateFlow<Album?>(null)
    val currentAlbum: StateFlow<Album?> = _currentAlbum.asStateFlow()

    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(application).build()

    val duration: Long
        get() = exoPlayer.duration

    private val updateProgressJob = viewModelScope.launch {
        while (true) {
            _currentPosition.value = exoPlayer.currentPosition
            delay(1000L)
        }
    }

    fun playSong(songId: Long) {
        viewModelScope.launch {
            val song = songRepository.getSongById(songId)
            _currentSong.value = song
            song?.let {
                val album = albumRepository.getAlbumById(song.albumId)
                _currentAlbumArt.value = album?.coverArt
                _currentAlbum.value = album
                val mediaItem = MediaItem.fromUri(it.fileName)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.play()
            }
        }
    }

    fun skipToNext() {
        // TODO: 다음 곡으로 이동 로직 구현
        // exoPlayer.seekToNext()
    }

    fun skipToPrevious() {
        // TODO: 이전 곡으로 이동 로직 구현
        // exoPlayer.seekToPrevious()
    }

    fun play() {
        exoPlayer.play()
        _isPlaying.value = true
    }

    fun pause() {
        exoPlayer.pause()
        _isPlaying.value = false
    }

    fun previous() {
        // TODO: 이전 곡 재생 로직 구현
    }

    fun next() {
        // TODO: 다음 곡 재생 로직 구현
    }

    fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
        _currentPosition.value = position
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }


}