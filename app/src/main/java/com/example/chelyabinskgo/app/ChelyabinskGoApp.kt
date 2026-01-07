package com.example.chelyabinskgo.app

import android.app.Application
import com.example.chelyabinskgo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChelyabinskGoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ChelyabinskGoApp)
            modules(appModule)
        }
    }
}