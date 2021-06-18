package com.craftie.data.remote

import com.craftie.data.model.Brewery
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class CraftieBreweriesAPI(private val httpClient: HttpClient) {

    suspend fun breweries(): List<Brewery> = httpClient.get(Endpoints.BREWERIES_ENDPOINT)

    suspend fun findBrewery(id: String) = httpClient.get<Brewery>(Endpoints.BREWERIES_ENDPOINT.plus("/$id"))

}