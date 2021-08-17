package com.craftie.android.presentation.discovery

import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.data.repository.CraftieBeersRepository
import com.craftie.data.repository.CraftieBreweriesRepository
import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.Beer
import com.craftie.data.model.Brewery
import com.craftie.data.model.Province
import com.craftie.data.repository.CraftieProvincesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DiscoveryUseCase @Inject constructor(
    private val beersRepository: CraftieBeersRepository,
    private val breweriesRepository: CraftieBreweriesRepository,
    private val provincesRepository: CraftieProvincesRepository,
    private val dispatchers: CoroutinesDispatcherProvider
) {

    fun build(): Flow<DiscoveryUiState> = flow {
        val beers = makeBeersCall()
        val breweries = makeBreweriesCall()
        val provinces = makeProvincesCall()

        if (
            beers is Outcome.Success
            && breweries is Outcome.Success
            && provinces is Outcome.Success
        ) {
            val data = DiscoveryUiData(breweries.value, beers.value, provinces.value)
            emit(DiscoveryUiState.Success(data))
            return@flow
        }

        emit(DiscoveryUiState.Error)
    }.flowOn(dispatchers.io)

    private suspend fun makeBeersCall() = makeApiCall(
        "Failure to retrieve beers"
    ) {
        fetchBeers()
    }

    private suspend fun makeBreweriesCall() = makeApiCall(
        "Failure to retrieve breweries"
    ) {
        fetchBreweries()
    }

    private suspend fun fetchBeers(): Outcome<List<Beer>> {
        val result = beersRepository.beers()

        return Outcome.Success(result)
    }

    private suspend fun fetchBreweries(): Outcome<List<Brewery>> {
        val result = breweriesRepository.breweries()

        return Outcome.Success(result)
    }

    private suspend fun makeProvincesCall() = makeApiCall(
        "Failure to retrieve provinces"
    ) {
        fetchProvinces()
    }

    private suspend fun fetchProvinces(): Outcome<List<Province>> {
        val result = provincesRepository.provinces()

        return Outcome.Success(result)
    }

}