package com.craftie.android.presentation.discovery

import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.data.repository.CraftieBeersRepository
import com.craftie.data.repository.CraftieBreweriesRepository
import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.Beer
import com.craftie.data.model.Brewery
import com.craftie.data.model.Pagination
import com.craftie.data.model.Province
import com.craftie.data.repository.CraftieProvincesRepository
import com.craftie.data.useCase.CraftieFilterUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(FlowPreview::class)
class DiscoveryUseCase @Inject constructor(
    private val dispatchers: CoroutinesDispatcherProvider,
    private val beersRepository: CraftieBeersRepository,
    private val breweriesRepository: CraftieBreweriesRepository,
    private val provincesRepository: CraftieProvincesRepository,
    private val filterUseCase: CraftieFilterUseCase,
) {

    fun build(): Flow<DiscoveryUiState> = flow {
        val beers = makeBeersCall()
        val breweries = makeBreweriesCall()
        val provinces = makeProvincesCall()
        val featuredBeer = makeFeaturedBeerCall()

        if (
            beers is Outcome.Success
            && breweries is Outcome.Success
            && provinces is Outcome.Success
            && featuredBeer is Outcome.Success
        ) {
            val data = DiscoveryUiData(
                breweries = breweries.value,
                beers = beers.value,
                provinces = provinces.value,
                featuredBeer = featuredBeer.value,
                filteredBeersByDate = filterUseCase.filterByCreationDate(beers.value)
            )
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

    private suspend fun makeFeaturedBeerCall() = makeApiCall("Failed to fetch featured beer") {
        fetchFeaturedBeer()
    }

    private suspend fun fetchFeaturedBeer(): Outcome<Beer> {
        val result = beersRepository.featuredBeer()

        return Outcome.Success(result)
    }

}