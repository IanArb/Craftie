package com.craftie.android.presentation.beerByProvince

import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.Beer
import com.craftie.data.repository.CraftieBeersRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BeersByProvinceUseCase @Inject constructor(
    private val beersRepository: CraftieBeersRepository
) {

    fun beersByProvince(province: String) = flow {
        val beers = makeBeersByProvinceCall(province)

        if (beers is Outcome.Success) {
            val value = beers.value
            if (value.isEmpty()) {
                emit(BeersByProvinceUiState.Empty)
                return@flow
            }
            emit(BeersByProvinceUiState.Success(value))
            return@flow
        }

        emit(BeersByProvinceUiState.Error)
    }

    private suspend fun makeBeersByProvinceCall(province: String) = makeApiCall(
        "Failed to fetch beer by $province"
    ) {
        fetchBeerByProvince(province)
    }

    private suspend fun fetchBeerByProvince(province: String): Outcome<List<Beer>> {
        val result = beersRepository.findBeersByProvince(province)

        return Outcome.Success(result)
    }
}