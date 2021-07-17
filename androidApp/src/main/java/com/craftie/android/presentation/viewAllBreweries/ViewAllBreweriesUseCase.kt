package com.craftie.android.presentation.viewAllBreweries

import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.Brewery
import com.craftie.data.repository.CraftieBreweriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViewAllBreweriesUseCase @Inject constructor(
    private val breweriesRepository: CraftieBreweriesRepository
) {

    fun breweries(): Flow<ViewAllBreweriesUiState> = flow {
        val result = makeBreweriesCall()

        if (result is Outcome.Success) {
            emit(ViewAllBreweriesUiState.Success(result.value))
            return@flow
        }

        emit(ViewAllBreweriesUiState.Error)
    }

    private suspend fun makeBreweriesCall() = makeApiCall(
        "Failure to retrieve breweries"
    ) {
        fetchBreweries()
    }

    private suspend fun fetchBreweries(): Outcome<List<Brewery>> {
        val result = breweriesRepository.breweries()

        return Outcome.Success(result)
    }
}