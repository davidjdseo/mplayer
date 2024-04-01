package com.example.mplayer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mplayer.domain.models.Album
import com.example.mplayer.domain.usecases.GetAlbumsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * 앨범 목록 화면의 뷰모델
 * @param getAlbumsUseCase 앨범 목록을 가져오는 유스케이스
 * @author david
 */
class AlbumListViewModel (
    private val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {

    /**
     * 앨범 목록을 담고 있는 StateFlow
     */
    val albums: StateFlow<List<Album>> = getAlbumsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}