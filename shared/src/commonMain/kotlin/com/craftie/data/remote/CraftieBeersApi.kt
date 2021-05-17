package com.craftie.data.remote

import com.craftie.data.model.Beer
import com.craftie.utils.Endpoints
import com.craftie.utils.Outcome
import com.craftie.utils.makeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.component.KoinComponent

class CraftieBeersApi(private val httpClient: HttpClient) : KoinComponent {

    suspend fun beers() = makeApiCall(
        { fetchBeers() },
        "Failed to fetch beers"
    )

    private suspend fun fetchBeers(): Outcome<List<Beer>> {
        val result = httpClient.get<List<Beer>>(Endpoints.BEERS_ENDPOINT)

        return Outcome.Success(result)
    }

    suspend fun findBeer(id: String) = makeApiCall(
        { fetchFindBeerById(id) },
        "Failed to find beer with id: $id"
    )

    private suspend fun fetchFindBeerById(id: String): Outcome<Beer> {
        val result = httpClient.get<Beer>(Endpoints.BEERS_ENDPOINT.plus("/$id"))

        return Outcome.Success(result)
    }

    suspend fun beersByType(type: String) = makeApiCall(
        { fetchBeersByType(type) },
        "Failed to find beers by type: $type"
    )

    private suspend fun fetchBeersByType(type: String): Outcome<List<Beer>> {
        val result = httpClient.get<List<Beer>>(Endpoints.BEERS_ENDPOINT) {
            parameter("type", type)
        }

        return Outcome.Success(result)
    }

    suspend fun beersByProvince(province: String) = makeApiCall(
        { fetchBeersByProvince(province) },
        "Failed to find beers by province: $province"
    )

    private suspend fun fetchBeersByProvince(province: String): Outcome<List<Beer>> {
        val result = httpClient.get<List<Beer>>(Endpoints.BEERS_ENDPOINT) {
            parameter("province", province)
        }

        return Outcome.Success(result)
    }

    suspend fun beersByBrewery(brewery: String) = makeApiCall(
        { fetchBeersByBrewery(brewery) },
        "Failed to find beer by brewery: $brewery"
    )

    private suspend fun fetchBeersByBrewery(brewery: String): Outcome<List<Beer>> {
        val result = httpClient.get<List<Beer>>(Endpoints.BEERS_ENDPOINT) {
            parameter("brewery", brewery)
        }

        return Outcome.Success(result)
    }

    suspend fun featuredBeer() = makeApiCall(
        { fetchFeaturedBeer() },
        "Failed to find featured beer"
    )

    private suspend fun fetchFeaturedBeer(): Outcome<Beer> {
        val result = httpClient.get<Beer>(Endpoints.BEERS_FEATURED_ENDPOINT)

        return Outcome.Success(result)
    }

}