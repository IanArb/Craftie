package com.craftie.android.presentation.home

import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.FavouriteBeerUiData
import com.craftie.data.model.ProvincesCount
import com.craftie.data.repository.ProvincesCountRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.math.roundToInt

class FavouriteBeersUseCase @Inject constructor(
    private val provincesCountRepository: ProvincesCountRepository,
) {

    suspend fun fetchFavouriteBeersByProvince(favourites: List<FavouriteBeerUiData>) = flow {
        val provinces = fetchProvincesCount()
        if (provinces is Outcome.Success) {
            val leinsterCount = favourites.count { favourite ->
                favourite.province == "Leinster"
            }

            val ulsterCount = favourites.count { favourite ->
                favourite.province == "Ulster"
            }

            val connaughtCount = favourites.count { favourite ->
                favourite.province == "Connaught"
            }

            val munsterCount = favourites.count { favourite ->
                favourite.province == "Munster"
            }

            favourites
                .map {
                    val value = provinces.value

                    val list = listOf(
                        FavouriteBeersUiData(
                            name = value.leinster.name,
                            value = calculatePercentage(leinsterCount, value.leinster.amount),
                            imageUrl = value.leinster.imageUrl
                        ),
                        FavouriteBeersUiData(
                            name = value.munster.name,
                            value = calculatePercentage(munsterCount, value.munster.amount),
                            imageUrl = value.munster.imageUrl
                        ),
                        FavouriteBeersUiData(
                            name = value.connaught.name,
                            value = calculatePercentage(connaughtCount, value.connaught.amount),
                            imageUrl = value.connaught.imageUrl
                        ),
                        FavouriteBeersUiData(
                            name = value.ulster.name,
                            value = calculatePercentage(ulsterCount, value.ulster.amount),
                            imageUrl = value.ulster.imageUrl
                        )
                    )
                   emit(FavouriteBeersUiState.Success(list))
                }
        } else {
            emit(FavouriteBeersUiState.Error)
        }
    }

    private suspend fun fetchProvincesCount(): Outcome<ProvincesCount> {
        return makeApiCall("Failed to fetch provinces count") {
            val result = provincesCountRepository.provincesCount()

            Outcome.Success(result)
        }
    }

    private fun calculatePercentage(value: Int, total: Int): Int {
        val count = value.toDouble()
        return (count.div(total)).times(100).roundToInt()
    }
}