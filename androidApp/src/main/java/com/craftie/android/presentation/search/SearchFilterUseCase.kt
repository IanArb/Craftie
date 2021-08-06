package com.craftie.android.presentation.search

import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.model.Beer
import com.craftie.data.repository.CraftieBeersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchFilterUseCase @Inject constructor(private val beersRepository: CraftieBeersRepository) {

    fun findBeersByKeyword(keyword: String): Flow<SearchFilterUiState> = flow {
        val result = makeFindBeersByKeywordCall(keyword)

        if (result is Outcome.Success) {
            if (result.value.isEmpty()) {
                emit(SearchFilterUiState.Empty)
                return@flow
            } else {
                emit(SearchFilterUiState.Success(result.value))
                return@flow
            }
        }

        emit(SearchFilterUiState.Error)
    }

    private suspend fun makeFindBeersByKeywordCall(keyword: String) = makeApiCall(
        "Failed to find any beers with $keyword"
    ) {
        fetchBeersByKeyword(keyword)
    }

    private suspend fun fetchBeersByKeyword(keyword: String): Outcome<List<Beer>> {
        val result = beersRepository.findBeersByKeyword(keyword)

        return Outcome.Success(result)
    }
}