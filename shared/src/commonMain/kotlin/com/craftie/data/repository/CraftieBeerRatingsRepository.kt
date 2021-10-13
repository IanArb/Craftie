package com.craftie.data.repository

import com.craftie.data.model.RatingRequest
import com.craftie.data.remote.CraftieBeerRatingsApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CraftieBeerRatingsRepository : KoinComponent {

    private val craftieBeerRatingsApi: CraftieBeerRatingsApi by inject()

    suspend fun ratingsByBeerId(beerId: String) = craftieBeerRatingsApi.ratingsByBeerId(beerId)

    suspend fun saveRating(ratingRequest: RatingRequest) = craftieBeerRatingsApi.saveRating(ratingRequest)

    suspend fun rating(beerId: String) = craftieBeerRatingsApi.rating(beerId)

}