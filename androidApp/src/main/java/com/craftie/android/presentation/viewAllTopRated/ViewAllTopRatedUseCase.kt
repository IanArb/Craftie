package com.craftie.android.presentation.viewAllTopRated

import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.Beer
import com.craftie.data.repository.CraftieBeersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViewAllTopRatedUseCase @Inject constructor(
    private val beersRepository: CraftieBeersRepository
) {

    fun beers(): Flow<ViewAllTopRatedUiState> = flow {
        val result = makeBeersCall()

        if (result is Outcome.Success) {
            emit(ViewAllTopRatedUiState.Success(result.value))
            return@flow
        }

        emit(ViewAllTopRatedUiState.Error)
    }

    private suspend fun makeBeersCall() = makeApiCall(
        "Failure to retrieve beers"
    ) {
        fetchBeers()
    }

    private suspend fun fetchBeers(): Outcome<List<Beer>> {
        val result = beersRepository.beers()

        return Outcome.Success(result)
    }
}