package com.craftie.data.repository

import com.craftie.data.remote.CraftieBeersApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CraftieBeersRepository : KoinComponent {

    private val craftieBeersApi: CraftieBeersApi by inject()

    suspend fun beers() = craftieBeersApi.beers()

    suspend fun findBeer(id: String) = craftieBeersApi.findBeer(id)

    suspend fun findBeersByType(type: String) = craftieBeersApi.beersByType(type)

    suspend fun findBeersByProvince(province: String) = craftieBeersApi.beersByProvince(province)

    suspend fun findBeersByBrewery(brewery: String) = craftieBeersApi.beersByBrewery(brewery)

    suspend fun featuredBeer() = craftieBeersApi.featuredBeer()

    suspend fun findBeersByKeyword(keyword: String) = craftieBeersApi.findBeersByKeyword(keyword)
}