package com.craftie.data.repository

import com.craftie.data.remote.CraftieBreweriesAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CraftieBreweriesRepository: KoinComponent {

    private val craftieBreweriesAPI: CraftieBreweriesAPI by inject()

    suspend fun breweries() = craftieBreweriesAPI.breweries()

    suspend fun findBrewery(id: String) = craftieBreweriesAPI.findBrewery(id)
}