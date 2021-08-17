package com.craftie.data.remote

import com.craftie.data.model.Province
import com.craftie.utils.Endpoints
import io.ktor.client.*
import io.ktor.client.request.*

class CraftieProvincesApi(private val httpClient: HttpClient) {

    suspend fun provinces(): List<Province> = httpClient.get(Endpoints.PROVINCES_ENDPOINT)
}