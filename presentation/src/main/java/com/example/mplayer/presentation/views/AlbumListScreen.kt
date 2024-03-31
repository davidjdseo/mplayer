package com.example.mplayer.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mplayer.domain.models.Album
import com.example.mplayer.presentation.viewmodels.AlbumListViewModel

/**
 * 앨범 목록 화면을 구성하는 컴포저블
 * @param viewModel 앨범 목록 뷰모델
 * @param onAlbumClick 앨범 클릭 시 호출될 콜백
 * @author david
 */
@Composable
fun AlbumListScreen(
    viewModel: AlbumListViewModel,
    onAlbumClick: (Long) -> Unit
) {
    val albums by viewModel.albums.collectAsState(initial = emptyList())
    
    LazyColumn {
        items(albums) { album ->
            AlbumItem(
                album = album,
                onAlbumClick = onAlbumClick
            )
        }
    }
}

/**
 * 개별 앨범 아이템을 표시하는 컴포저블
 * @param album 앨범 정보
 * @param onAlbumClick 앨범 클릭 시 호출될 콜백
 * @author david
 */ 
@Composable
fun AlbumItem(
    album: Album,
    onAlbumClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onAlbumClick(album.id) },
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = album.coverArt),
                contentDescription = "${album.title} cover",
                modifier = Modifier.size(64.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = album.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = album.artist,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}