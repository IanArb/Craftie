package com.craftie.data.repository

import com.craftie.data.remote.CraftieBreweriesAPI

class CraftieBreweriesRepository(private val craftieBreweriesAPI: CraftieBreweriesAPI) {

    suspend fun breweries() = craftieBreweriesAPI.breweries()

    suspend fun findBrewery(id: String) = craftieBreweriesAPI.findBrewery(id)
}