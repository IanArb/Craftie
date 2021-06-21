package com.craftie.android.presentation.discovery.usecase

import com.craftie.android.presentation.discovery.model.DiscoveryUiData
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.data.repository.CraftieBeersRepository
import com.craftie.data.repository.CraftieBreweriesRepository
import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.Beer
import com.craftie.data.model.Brewery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DiscoveryUseCase @Inject constructor(
    private val beersRepository: CraftieBeersRepository,
    private val breweriesRepository: CraftieBreweriesRepository,
    private val dispatchers: CoroutinesDispatcherProvider
) {

    fun build(): Flow<Outcome<DiscoveryUiData>> = flow {
        val beers = makeBeersCall()
        val breweries = makeBreweriesCall()

        if (beers is Outcome.Success && breweries is Outcome.Success) {
            val data = DiscoveryUiData(breweries.value, beers.value)
            emit(Outcome.Success(data))
            return@flow
        }

        emit(Outcome.Error("Failed to retrieve data"))
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

}