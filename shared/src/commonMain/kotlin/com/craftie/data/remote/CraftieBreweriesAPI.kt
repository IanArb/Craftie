package com.craftie.data.remote

import com.craftie.data.model.Brewery
import com.craftie.data.model.Pagination
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*

class CraftieBreweriesAPI(private val httpClient: HttpClient) {

    companion object {
        private const val PAGE_KEY = "page"
    }

    suspend fun breweriesPageable(page: Int = 1): Pagination<Brewery> {
        val response =  httpClient.get(Endpoints.BREWERIES_PAGINATION_ENDPOINT) {
            parameter(PAGE_KEY, page)
        }
        return response.body()
    }

}