package com.craftie.android.presentation.viewAllRatings

import com.craftie.android.authentication.TokenUseCase
import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.RatingResponse
import com.craftie.data.repository.CraftieBeerRatingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViewAllRatingsUseCase @Inject constructor(
    private val ratingsRepository: CraftieBeerRatingsRepository,
    private val tokenUseCase: TokenUseCase,
) {

    fun build(beerId: String): Flow<ViewAllRatingsUiState> = flow {
        val ratings = fetchRatings(beerId)

        if (ratings is Outcome.Success) {
            if (ratings.value.isEmpty()) {
                emit(ViewAllRatingsUiState.Empty)
                return@flow
            }
            emit(ViewAllRatingsUiState.Success(ratings.value))
            return@flow
        }

        if (ratings is Outcome.UnauthorisedError) {
            tokenUseCase.login()
        }

        emit(ViewAllRatingsUiState.Error)
    }

    private suspend fun fetchRatings(beerId: String) = makeApiCall("") {
        doFetchRatings(beerId)
    }

    private suspend fun doFetchRatings(beerId: String): Outcome<List<RatingResponse>> {
        val results = ratingsRepository.ratingsByBeerId(beerId)

        return Outcome.Success(results)
    }
}