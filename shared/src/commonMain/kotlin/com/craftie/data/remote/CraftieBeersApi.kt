package com.craftie.data.remote

import com.craftie.data.model.Beer
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.*
import org.koin.core.component.KoinComponent

class CraftieBeersApi(private val httpClient: HttpClient) : KoinComponent {

    suspend fun beers(): List<Beer> = httpClient.get(Endpoints.BEERS_ENDPOINT)

    suspend fun findBeer(id: String) = httpClient.get<Beer>(Endpoints.BEERS_ENDPOINT.plus("/$id"))

    suspend fun beersByType(type: String): List<Beer> {
        return httpClient.get(Endpoints.BEERS_ENDPOINT) {
            parameter("type", type)
        }
    }

    suspend fun beersByProvince(province: String): List<Beer> {
        return httpClient.get(Endpoints.BEERS_ENDPOINT) {
            parameter("province", province)
        }
    }

    suspend fun beersByBrewery(brewery: String): List<Beer> {
        return httpClient.get(Endpoints.BEERS_ENDPOINT) {
            parameter("brewery", brewery)
        }
    }

    suspend fun featuredBeer(): Beer = httpClient.get(Endpoints.BEERS_FEATURED_ENDPOINT)

    suspend fun findBeersByKeyword(keyword: String): List<Beer> {
        return httpClient.get(Endpoints.BEERS_ENDPOINT) {
            parameter("keyword", keyword)
        }
    }
}