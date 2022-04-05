package com.craftie.data.remote

import com.craftie.data.model.RatingRequest
import com.craftie.data.model.RatingResponse
import com.craftie.data.model.RatingResult
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class CraftieBeerRatingsApi(
    private val httpClient: HttpClient,
    private val authenticationApi: CraftieAuthenticationApi,
) {

    suspend fun saveRating(ratingRequest: RatingRequest): String {
        val token = authenticationApi.login().token
        httpClient.post(Endpoints.AVERAGE_RATING_ENDPOINT) {
            headers {
                append(HttpHeaders.Authorization, token)
            }
            contentType(ContentType.Application.Json)
            setBody(ratingRequest)
        }

        return "Successfully sent request"
    }

    suspend fun ratingsByBeerId(beerId: String): List<RatingResponse> {
        val token = authenticationApi.login().token
        val response = httpClient.get(Endpoints.RATINGS_ENDPOINT.plus("/$beerId")) {
            headers {
                append(HttpHeaders.Authorization, token)
            }
        }
        return response.body()
    }

    suspend fun rating(beerId: String): RatingResult {
        val token = authenticationApi.login().token
        val response = httpClient.get(Endpoints.AVERAGE_RATING_ENDPOINT.plus("/$beerId")) {
            headers {
                append(HttpHeaders.Authorization, token)
            }
        }
        return response.body()
    }
}