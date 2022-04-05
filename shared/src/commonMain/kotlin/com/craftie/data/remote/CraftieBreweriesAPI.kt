package com.craftie.data.remote

import com.craftie.data.model.Brewery
import com.craftie.data.model.Pagination
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.HttpHeaders

class CraftieBreweriesAPI(
    private val httpClient: HttpClient,
    private val authenticationApi: CraftieAuthenticationApi,
) {

    suspend fun breweriesPageable(page: Int = 1): Pagination<Brewery> {
        val login = authenticationApi.login().token
        val response = httpClient.get(Endpoints.BREWERIES_ENDPOINT) {
            headers {
                append(HttpHeaders.Authorization, login)
            }
            parameter(PAGE_KEY, page)
        }
        return response.body()
    }

    companion object {
        private const val PAGE_KEY = "page"
    }

}