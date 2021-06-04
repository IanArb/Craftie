package com.craftie.android.presentation.discovery.usecase

import com.craftie.android.presentation.discovery.model.DiscoveryUiData
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.data.repository.CraftieBeersRepository
import com.craftie.data.repository.CraftieBreweriesRepository
import com.craftie.utils.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DiscoveryUseCase @Inject constructor(
    private val beersRepository: CraftieBeersRepository,
    private val breweriesRepository: CraftieBreweriesRepository,
    private val dispatchers: CoroutinesDispatcherProvider
) {

    fun build(): Flow<Outcome<DiscoveryUiData>> = flow {
        val beers = beersRepository.beers()
        val breweries = breweriesRepository.breweries()

        if (beers is Outcome.Success && breweries is Outcome.Success) {
            val data = DiscoveryUiData(breweries.value, beers.value)
            emit(Outcome.Success(data))
            return@flow
        }

        emit(Outcome.Error("Failed to retrieve data"))
    }.flowOn(dispatchers.io)

}