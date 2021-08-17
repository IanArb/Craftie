package com.craftie.data.repository

import com.craftie.data.remote.CraftieBreweriesAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CraftieBreweriesRepository: KoinComponent {

    private val craftieBreweriesApi: CraftieBreweriesAPI by inject()

    suspend fun breweries() = craftieBreweriesApi.breweries()

    suspend fun findBrewery(id: String) = craftieBreweriesApi.findBrewery(id)
}