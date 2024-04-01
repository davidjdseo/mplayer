package com.example.mplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mplayer.presentation.viewmodels.AlbumListViewModel
import com.example.mplayer.presentation.viewmodels.PlayerViewModel
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
    val playerViewModel = getViewModel<PlayerViewModel>()

    NavHost(navController = navController, startDestination = "album_list") {
        composable("album_list") {
            AlbumListScreen(
                viewModel = albumListViewModel,
                onAlbumClick = { albumId ->
                    navController.navigate("song_list/$albumId")
                }
            )
        }
        composable(
            route = "song_list/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.LongType }),
            enterTransition = {
                slideInVertically(initialOffsetY = { it })
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { it })
            }
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getLong("albumId") ?: -1L
            SongListScreen(
                viewModel = albumListViewModel,
                albumId = albumId,
                onSongClick = { songId ->
                    playerViewModel.playSong(songId)
                    navController.navigate("player")
                }
            )
        }
        composable(
            route = "player",
            enterTransition = {
                slideInVertically(initialOffsetY = { it })
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { it })
            }
        ) {
            PlayerScreen(viewModel = playerViewModel)
        }
    }
}