package com.craftie.android.ui.presentation.discovery.usecase

import com.craftie.android.ui.presentation.discovery.model.DiscoveryUiData
import com.craftie.data.repository.CraftieBeersRepository
import com.craftie.data.repository.CraftieBreweriesRepository
import com.craftie.utils.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class DiscoveryUseCase @Inject constructor(
    private val beersRepository: CraftieBeersRepository,
    private val breweriesRepository: CraftieBreweriesRepository
) {

    fun build(): Flow<Outcome<DiscoveryUiData>> = callbackFlow {
        val beers = beersRepository.beers()
        val breweries = breweriesRepository.breweries()

        if (beers is Outcome.Success && breweries is Outcome.Success) {
            val data = DiscoveryUiData(breweries.value, beers.value)
            offer(Outcome.Success(data))
            return@callbackFlow
        }

        offer(Outcome.Error("Failed to retrieve data"))
    }

}