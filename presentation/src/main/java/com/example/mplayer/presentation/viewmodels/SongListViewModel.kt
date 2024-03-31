package com.example.mplayer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mplayer.domain.models.Song
import com.example.mplayer.domain.usecases.GetSongsUseCase
import kotlinx.coroutines.flow.*

/**
 * 곡 목록 화면의 ViewModel
 * @param getSongsUseCase 곡 목록을 가져오는 UseCase
 * @author david
 */
class SongListViewModel(
    private val getSongsUseCase: GetSongsUseCase
) : ViewModel() {

    /**
     * 선택된 앨범의 ID를 저장하는 MutableStateFlow
     */
    private val _albumId = MutableStateFlow<Long>(-1L)

    /**
     * 선택된 앨범의 ID를 공개하는 StateFlow
     */
    val albumId: StateFlow<Long> = _albumId.asStateFlow()

    /**
     * 곡 목록을 담고 있는 StateFlow
     */
    val songs: StateFlow<List<Song>> = _albumId
        .flatMapLatest { getSongsUseCase(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * 새로운 앨범을 선택하는 메서드
     * @param albumId 선택할 앨범의 ID
     */
    fun selectAlbum(albumId: Long) {
        _albumId.value = albumId
    }
}