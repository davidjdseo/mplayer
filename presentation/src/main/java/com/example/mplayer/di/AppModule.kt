package com.example.mplayer.di
import com.example.mplayer.data.datasources.AlbumLocalDataSource
import com.example.mplayer.data.datasources.SongLocalDataSource
import com.example.mplayer.data.repositories.AlbumRepositoryImpl
import com.example.mplayer.data.repositories.SongRepositoryImpl
import com.example.mplayer.domain.repositories.AlbumRepository
import com.example.mplayer.domain.repositories.SongRepository
import com.example.mplayer.domain.usecases.GetAlbumsUseCase
import com.example.mplayer.domain.usecases.GetSongsUseCase
import org.koin.android.ext.koin.androidApplication
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

    single { GetAlbumsUseCase(get()) }
    single { GetSongsUseCase(get()) }

}