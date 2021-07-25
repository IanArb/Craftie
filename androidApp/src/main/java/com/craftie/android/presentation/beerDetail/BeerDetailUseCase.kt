package com.craftie.android.presentation.beerDetail

import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.Beer
import com.craftie.data.repository.CraftieBeersRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BeerDetailUseCase @Inject constructor(private val beersRepository: CraftieBeersRepository) {

    fun beer(id: String) = flow {
        val result = makeFindBeerByIdCall(id)

        if (result is Outcome.Success) {
            emit(BeerDetailUiState.Success(result.value))
            return@flow
        }

        emit(BeerDetailUiState.Error)
    }

    private suspend fun makeFindBeerByIdCall(id: String) = makeApiCall(
        "Failed to find beer by id: $id"
    ) {
        findBeerById(id)
    }

    private suspend fun findBeerById(id: String): Outcome<Beer> {
        val result = beersRepository.findBeer(id)

        return Outcome.Success(result)
    }
}