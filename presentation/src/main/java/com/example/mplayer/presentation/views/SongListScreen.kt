package com.example.mplayer.presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mplayer.domain.models.Song
import com.example.mplayer.presentation.viewmodels.SongListViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun SongListScreen(
    viewModel: SongListViewModel = getViewModel(),
    onSongClick: (Long) -> Unit
) {
    val songs by viewModel.songs.collectAsState()

    LazyColumn {
        items(songs) { song ->
            SongItem(
                song = song,
                onSongClick = onSongClick
            )
        }
    }
}

@Composable
fun SongItem(
    song: Song,
    onSongClick: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSongClick(song.id) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = song.trackNumber.toString(),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.width(24.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = song.title,
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = song.artist,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = formatDuration(song.duration),
            style = MaterialTheme.typography.body2
        )
    }
}

fun formatDuration(duration: Long): String {
    val minutes = duration / 1000 / 60
    val seconds = duration / 1000 % 60
    return "%d:%02d".format(minutes, seconds)
}