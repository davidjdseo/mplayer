package com.example.mplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mplayer.presentation.viewmodels.AlbumListViewModel
import com.example.mplayer.presentation.viewmodels.PlayerViewModel
import com.example.mplayer.presentation.viewmodels.SongListViewModel
import com.example.mplayer.presentation.views.AlbumListScreen
import com.example.mplayer.presentation.views.PlayerScreen
import com.example.mplayer.presentation.views.SongListScreen
import com.example.mplayer.ui.theme.MplayerTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MplayerTheme {
                MainScreen()
            }
        }
    }
}
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val albumListViewModel = getViewModel<AlbumListViewModel>()
    val songListViewModel = getViewModel<SongListViewModel>()
    val playerViewModel = getViewModel<PlayerViewModel>()

    NavHost(navController = navController, startDestination = "album_list") {
        composable("album_list") {
            AlbumListScreen(
                viewModel = albumListViewModel,
                onAlbumClick = { albumId ->
                    songListViewModel.selectAlbum(albumId)
                    navController.navigate("song_list")
                }
            )
        }
        composable("song_list") {
            SongListScreen(
                viewModel = songListViewModel,
                onSongClick = { songId ->
                    playerViewModel.playSong(songId)
                    navController.navigate("player")
                }
            )
        }
        composable("player") {
            PlayerScreen(viewModel = playerViewModel)
        }
    }
}