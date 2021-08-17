package com.craftie.data.repository

import com.craftie.data.model.Province
import com.craftie.data.remote.CraftieProvincesApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CraftieProvincesRepository: KoinComponent {

    private val craftieProvincesApi: CraftieProvincesApi by inject()

    suspend fun provinces(): List<Province> = craftieProvincesApi.provinces()
}