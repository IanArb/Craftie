package com.craftie.data.remote

import com.craftie.data.model.RatingRequest
import com.craftie.data.model.RatingResponse
import com.craftie.data.model.RatingResult
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CraftieBeerRatingsApi(private val httpClient: HttpClient) {

    suspend fun saveRating(ratingRequest: RatingRequest): String {
        httpClient.post(Endpoints.AVERAGE_RATING_ENDPOINT) {
            setBody(ratingRequest)
            contentType(ContentType.Application.Json)
        }

        return "Successfully sent request"
    }

    suspend fun ratingsByBeerId(beerId: String): List<RatingResponse> {
        val response = httpClient.get(Endpoints.RATINGS_ENDPOINT.plus("/$beerId"))
        return response.body()
    }

    suspend fun rating(beerId: String): RatingResult {
        val response = httpClient.get(Endpoints.AVERAGE_RATING_ENDPOINT.plus("/$beerId"))
        return response.body()
    }
}