package com.craftie.data.remote

import com.craftie.data.model.Brewery
import com.craftie.utils.Endpoints
import com.craftie.utils.Outcome
import com.craftie.utils.makeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class CraftieBreweriesAPI(private val httpClient: HttpClient) {

    suspend fun breweries() = makeApiCall(
        { fetchBreweries() },
        "Failed to find breweries"
    )

    private suspend fun fetchBreweries(): Outcome<List<Brewery>> {
        val result = httpClient.get<List<Brewery>>(Endpoints.BREWERIES_ENDPOINT)

        return Outcome.Success(result)
    }

    suspend fun findBrewery(id: String) = makeApiCall(
        { findBreweryById(id) },
        "Failed to find brewery by id: $id"
    )

    private suspend fun findBreweryById(id: String): Outcome<List<Brewery>> {
        val result = httpClient.get<List<Brewery>>(Endpoints.BREWERIES_ENDPOINT.plus("/$id"))

        return Outcome.Success(result)
    }
}