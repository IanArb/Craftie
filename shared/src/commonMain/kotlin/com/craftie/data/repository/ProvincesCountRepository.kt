package com.craftie.data.repository

import com.craftie.data.model.ProvincesCount
import com.craftie.data.remote.CraftieProvincesCountApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProvincesCountRepository : KoinComponent {

    private val provincesCountApi: CraftieProvincesCountApi by inject()

    suspend fun provincesCount(): ProvincesCount = provincesCountApi.provincesCount()
}