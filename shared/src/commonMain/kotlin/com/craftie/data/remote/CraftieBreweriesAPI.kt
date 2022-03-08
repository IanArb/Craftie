package com.craftie.data.remote

import com.craftie.data.model.Brewery
import com.craftie.data.model.Pagination
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.*

class CraftieBreweriesAPI(private val httpClient: HttpClient) {

    companion object {
        private const val PAGE_KEY = "key"
    }

    suspend fun breweries(): List<Brewery> = httpClient.get(Endpoints.BREWERIES_ENDPOINT)

    suspend fun breweriesPageable(page: Int = 1): Pagination<Brewery> {
        return httpClient.get(Endpoints.BREWERIES_PAGINATION_ENDPOINT) {
            parameter(PAGE_KEY, page)
        }
    }

    suspend fun findBrewery(id: String) = httpClient.get<Brewery>(Endpoints.BREWERIES_ENDPOINT.plus("/$id"))
}