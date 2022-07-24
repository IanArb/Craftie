package com.craftie.android

import android.app.Application
import com.craftie.android.util.BaseUrlResolver
import com.craftie.di.initCraftie
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import javax.inject.Inject

@HiltAndroidApp
class CraftieApp : Application() {

    @Inject
    lateinit var baseUrlResolver: BaseUrlResolver

    override fun onCreate() {
        super.onCreate()

        initCraftie(baseUrl = baseUrlResolver.resolveBaseUrl()) {
            androidLogger()
            androidContext(this@CraftieApp)
        }
    }
}