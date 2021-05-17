package com.craftie.data.repository

import com.craftie.data.remote.CraftieBeersApi
import org.koin.core.component.KoinComponent

class CraftieBeersRepository(private val craftieBeersApi: CraftieBeersApi) : KoinComponent {

    suspend fun beers() = craftieBeersApi.beers()

    suspend fun findBeer(id: String) = craftieBeersApi.findBeer(id)

    suspend fun findBeersByType(type: String) = craftieBeersApi.beersByType(type)

    suspend fun findBeersByProvince(province: String) = craftieBeersApi.beersByProvince(province)

    suspend fun findBeersByBrewery(brewery: String) = craftieBeersApi.beersByBrewery(brewery)

    suspend fun featuredBeer() = craftieBeersApi.featuredBeer()
}