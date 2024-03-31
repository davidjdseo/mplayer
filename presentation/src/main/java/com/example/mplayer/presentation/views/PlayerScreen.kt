package com.example.mplayer.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mplayer.presentation.viewmodels.PlayerViewModel

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel,
) {
    val currentSong by viewModel.currentSong.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = currentSong?.title ?: "",
            style = MaterialTheme.typography.h5
        )
        Text(
            text = currentSong?.artist ?: "",
            style = MaterialTheme.typography.subtitle1
        )
        // TODO: 재생 컨트롤러 추가
    }
}