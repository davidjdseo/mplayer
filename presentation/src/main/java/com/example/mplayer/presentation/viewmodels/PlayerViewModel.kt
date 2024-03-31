package com.example.mplayer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mplayer.domain.models.Song
import com.example.mplayer.domain.repositories.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
     * 새로운 곡을 재생하는 메서드
     * @param songId 재생할 곡의 ID
     */

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