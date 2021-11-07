package com.craftie.android.presentation.ratings

import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.RatingRequest
import com.craftie.data.model.RatingResponse
import com.craftie.data.model.RatingResult
import com.craftie.data.repository.CraftieBeerRatingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveBeerRatingUseCase @Inject constructor(private val craftieBeerRatingsRepository: CraftieBeerRatingsRepository) {

    suspend fun saveRating(ratingRequest: RatingRequest): Flow<SendRatingUiState> = flow {
        val result = sendRating(ratingRequest)

        if (result is Outcome.Success) {
            emit(SendRatingUiState.Success(result.value))
        } else {
            emit(SendRatingUiState.Error)
        }
    }

    private suspend fun sendRating(ratingRequest: RatingRequest) = makeApiCall("Could not send rating") {
        doSendRating(ratingRequest)
    }

    private suspend fun doSendRating(ratingRequest: RatingRequest): Outcome<String> {
        craftieBeerRatingsRepository.saveRating(ratingRequest)

        return if (ratingRequest.beerId.isNotEmpty()) {
            Outcome.Success("Successfully sent request")
        } else {
            Outcome.Error("Error sending rating with id: ${ratingRequest.beerId}")
        }
    }

    suspend fun ratingByBeerId(beerId: String): Flow<RatingUiState> = flow {
        val rating = fetchRating(beerId)

        if (rating is Outcome.Success) {
            emit(RatingUiState.Success(rating.value))
            return@flow
        }

        emit(RatingUiState.Error)
    }

    private suspend fun fetchRating(beerId: String) = makeApiCall(
        "Failed to fetch rating by $beerId"
    ) {
        doFetchRating(beerId)
    }

    private suspend fun doFetchRating(beerId: String): Outcome<RatingResult> {
        val rating = craftieBeerRatingsRepository.rating(beerId)

        return Outcome.Success(rating)
    }
}