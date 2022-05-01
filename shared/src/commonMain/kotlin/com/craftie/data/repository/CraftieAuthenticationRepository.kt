package com.craftie.data.repository

import com.craftie.data.remote.CraftieAuthenticationApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CraftieAuthenticationRepository : KoinComponent {

    private val authenticationApi: CraftieAuthenticationApi by inject()

    suspend fun login(username: String, password: String) = authenticationApi.login(username, password)
}