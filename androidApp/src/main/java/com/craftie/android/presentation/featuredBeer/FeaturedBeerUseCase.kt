package com.craftie.android.presentation.featuredBeer

import com.craftie.android.authentication.TokenUseCase
import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.Beer
import com.craftie.data.repository.CraftieBeersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FeaturedBeerUseCase @Inject constructor(
    private val repository: CraftieBeersRepository,
    private val tokenUseCase: TokenUseCase,
) {

    suspend fun featuredBeer(): Flow<FeaturedBeerUiState> = flow {
        val result = makeFeaturedBeerCall()

        if (result is Outcome.Success) {
            emit(FeaturedBeerUiState.Success(result.value))
            return@flow
        }

        if (result is Outcome.UnauthorisedError) {
            tokenUseCase.login()
        }

        emit(FeaturedBeerUiState.Error)
    }

    private suspend fun makeFeaturedBeerCall() = makeApiCall(
        "Failed to fetch featured beer"
    ) {
        fetchFeaturedBeer()
    }

    private suspend fun fetchFeaturedBeer(): Outcome<Beer> {
        val result = repository.featuredBeer()

        return Outcome.Success(result)
    }

}