package com.example.mplayer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mplayer.domain.models.Album
import com.example.mplayer.domain.models.Song
import com.example.mplayer.domain.usecases.GetAlbumsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * 앨범 목록 화면의 뷰모델
 * @param getAlbumsUseCase 앨범 목록을 가져오는 유스케이스
 * @author david
 */
class AlbumListViewModel (
    private val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums.asStateFlow()

    private val _selectedAlbumId = MutableStateFlow(-1L)
    val selectedAlbumId: StateFlow<Long> = _selectedAlbumId.asStateFlow()

    val selectedAlbumSongs: List<Song>
        get() = albums.value.find { it.id == selectedAlbumId.value }?.songs ?: emptyList()

    init {
        viewModelScope.launch {
            getAlbumsUseCase().collect { albums ->
                _albums.value = albums
            }
        }
    }

    fun selectAlbum(albumId: Long) {
        _selectedAlbumId.value = albumId
    }

}