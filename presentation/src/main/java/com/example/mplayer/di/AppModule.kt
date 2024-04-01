package com.example.mplayer.di
import com.example.mplayer.data.datasources.AlbumLocalDataSource
import com.example.mplayer.data.datasources.SongLocalDataSource
import com.example.mplayer.data.repositories.AlbumRepositoryImpl
import com.example.mplayer.data.repositories.SongRepositoryImpl
import com.example.mplayer.domain.repositories.AlbumRepository
import com.example.mplayer.domain.repositories.SongRepository
import com.example.mplayer.domain.usecases.GetAlbumsUseCase
import com.example.mplayer.domain.usecases.GetSongUseCase
import com.example.mplayer.domain.usecases.GetSongsUseCase
import com.example.mplayer.presentation.viewmodels.AlbumListViewModel
import com.example.mplayer.presentation.viewmodels.PlayerViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin 앱 모듈
 * @author david
 */
val appModule = module {
    single { androidApplication().applicationContext }

    single { AlbumLocalDataSource(get()) }
    single { SongLocalDataSource(get()) }

    single<AlbumRepository> { AlbumRepositoryImpl(get()) }
    single<SongRepository> { SongRepositoryImpl(get()) }

    factory { GetAlbumsUseCase(get()) }
    factory { GetSongsUseCase(get()) }
    factory { GetSongUseCase(get()) }

    viewModel { AlbumListViewModel(get()) }
    viewModel { PlayerViewModel(get(), get(), androidApplication()) }


}