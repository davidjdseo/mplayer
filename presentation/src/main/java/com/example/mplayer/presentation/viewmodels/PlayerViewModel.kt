package com.example.mplayer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mplayer.domain.models.Song
import com.example.mplayer.domain.repositories.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 플레이어 화면의 ViewModel
 * @author david
 */
class PlayerViewModel(private val songRepository: SongRepository) : ViewModel() {
    /**
     * 현재 재생 중인 곡의 ID를 저장하는 MutableStateFlow
     */
    private val _currentSongId = MutableStateFlow<Long>(-1L)

    /**
     * 현재 재생 중인 곡의 ID를 공개하는 StateFlow
     */
    val currentSongId: StateFlow<Long> = _currentSongId

    /**
     * 현재 재생중 여부를 판단하는 MutableStateFlow
     */
    private val _isPlaying = MutableStateFlow(false)

    /**
     * 현재 재생중 여부를 공개하는 StateFlow
     */
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    /**
     * 새로운 곡을 재생하는 메서드
     * @param songId 재생할 곡의 ID
     */

    fun play() {
        _isPlaying.value = true
        // TODO: 실제 재생 로직 구현
    }

    fun pause() {
        _isPlaying.value = false
        // TODO: 실제 일시정지 로직 구현
    }

    fun skipToNext() {
        // TODO: 다음 곡으로 이동 로직 구현
    }

    fun skipToPrevious() {
        // TODO: 이전 곡으로 이동 로직 구현
    }

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong

    fun playNewSong(songId: Long) {
        viewModelScope.launch {
            val song = songRepository.getSongById(songId)
            _currentSongId.value = songId
            _currentSong.value = song
        }
    }
}