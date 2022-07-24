package com.craftie.data.remote

import com.craftie.data.model.Brewery
import com.craftie.data.model.Info
import com.craftie.data.model.Pagination
import com.craftie.data.settings.SettingsRepository
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.*
import io.ktor.http.HttpHeaders

class CraftieBreweriesAPI(
    private val httpClient: HttpClient,
    private val settingsRepository: SettingsRepository,
) {

    suspend fun breweriesPageable(page: Int = 1): Pagination<Brewery> {
        val token = settingsRepository.token()
        val endpoint = settingsRepository.baseUrl().plus(Endpoints.BREWERIES_ENDPOINT)
        val response = httpClient.get(endpoint) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
            parameter(PAGE_KEY, page)
        }
        return response.body()
    }

    companion object {
        private const val PAGE_KEY = "page"
    }

}