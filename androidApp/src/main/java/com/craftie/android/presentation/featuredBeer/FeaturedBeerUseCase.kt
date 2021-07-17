package com.craftie.android.presentation.featuredBeer

import com.craftie.android.presentation.featuredBeer.FeaturedBeerUiState
import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.Beer
import com.craftie.data.repository.CraftieBeersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FeaturedBeerUseCase @Inject constructor(
    private val repository: CraftieBeersRepository
) {

    suspend fun featuredBeer(): Flow<FeaturedBeerUiState> = flow {
        val result = makeFeaturedBerrCall()

        if (result is Outcome.Success) {
            emit(FeaturedBeerUiState.Success(result.value))
        } else {
            emit(FeaturedBeerUiState.Error)
        }
    }

    private suspend fun makeFeaturedBerrCall() = makeApiCall(
        "Failed to fetch featured beer"
    ) {
        fetchFeaturedBeer()
    }

    private suspend fun fetchFeaturedBeer(): Outcome<Beer> {
        val result = repository.featuredBeer()

        return Outcome.Success(result)
    }

}