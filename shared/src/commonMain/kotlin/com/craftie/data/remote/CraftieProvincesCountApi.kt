package com.craftie.data.remote

import com.craftie.data.model.ProvincesCount
import com.craftie.data.settings.SettingsRepository
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders

class CraftieProvincesCountApi(
    private val httpClient: HttpClient,
    private val settingsRepository: SettingsRepository,
) {

    suspend fun provincesCount(): ProvincesCount {
        val token = settingsRepository.token()
        val endpoint = settingsRepository.baseUrl().plus(Endpoints.PROVINCES_COUNT_ENDPOINT)
        val response = httpClient.get(endpoint) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
        return response.body()
    }

}