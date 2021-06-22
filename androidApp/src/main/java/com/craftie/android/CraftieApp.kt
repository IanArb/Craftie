package com.craftie.android

import android.app.Application
import com.craftie.di.initKoin
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

@HiltAndroidApp
class CraftieApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@CraftieApp)
        }
    }
}