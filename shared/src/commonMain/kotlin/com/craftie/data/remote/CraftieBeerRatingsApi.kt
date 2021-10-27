package com.craftie.data.remote

import com.craftie.data.model.RatingRequest
import com.craftie.data.model.RatingResponse
import com.craftie.data.model.RatingResult
import com.craftie.utils.Endpoints
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class CraftieBeerRatingsApi(private val httpClient: HttpClient) {

    suspend fun saveRating(ratingRequest: RatingRequest): String {
        httpClient.post<HttpResponse> {
            url(Endpoints.AVERAGE_RATING_ENDPOINT)
            contentType(ContentType.Application.Json)
            body = ratingRequest
        }

        return "Successfully sent request"
    }

    suspend fun ratingsByBeerId(beerId: String): List<RatingResponse> = httpClient.get(Endpoints.RATINGS_ENDPOINT.plus("/$beerId"))

    suspend fun rating(beerId: String): RatingResult = httpClient.get(Endpoints.AVERAGE_RATING_ENDPOINT.plus("/$beerId"))
}