package com.example.mplayer

import android.app.Application
import com.example.mplayer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * 앱의 메인 Application 클래스
 * @author david
 */
class MPlayerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MPlayerApplication)
            modules(appModule)
        }
    }
}