package com.craftie.data.remote

import com.craftie.data.model.JwtToken
import com.craftie.data.model.Login
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CraftieAuthenticationApi(private val httpClient: HttpClient) {

    suspend fun login(username: String, password: String): JwtToken {
        val response = httpClient.post(Endpoints.AUTHENTICATION_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(Login(username, password))
        }

        return response.body()
    }
}