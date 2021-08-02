package com.craftie.android.presentation.search

import com.craftie.data.model.Beer

sealed class SearchFilterUiState {
    data class Success(val beers: List<Beer>) : SearchFilterUiState()
    object Empty : SearchFilterUiState()
    object Error : SearchFilterUiState()
    object Loading : SearchFilterUiState()
}