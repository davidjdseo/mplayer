package com.example.mplayer.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mplayer.R
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

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(albums.size) { index ->
            val album = albums[index]
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
    val albumArt = album.coverArt
    Card(
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .clickable { onAlbumClick(album.id) }
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(albumArt)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = album.title,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = album.artist,
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}