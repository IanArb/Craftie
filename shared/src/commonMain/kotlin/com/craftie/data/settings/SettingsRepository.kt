package com.craftie.data.settings

import com.russhwolf.settings.Settings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsRepository: KoinComponent {

    private val settings: Settings by inject()

    fun saveToken(token: String) {
        settings.putString(TOKEN_KEY, token)
    }

    fun token(): String = settings.getString(TOKEN_KEY)

    fun clear() = settings.clear()

    companion object {
        const val TOKEN_KEY = "TOKEN_KEY"
    }
}