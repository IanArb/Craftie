package com.craftie.data.remote

import com.craftie.data.model.Province
import com.craftie.utils.Endpoints
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.HttpHeaders

class CraftieProvincesApi(
    private val httpClient: HttpClient,
    private val authenticationApi: CraftieAuthenticationApi,
) {

    suspend fun provinces(): List<Province> {
        val token = authenticationApi.login().token
        val response = httpClient.get(Endpoints.PROVINCES_ENDPOINT) {
            headers {
                append(HttpHeaders.Authorization, token)
            }
        }
        return response.body()
    }
}