package com.craftie.android.presentation.beerDetail

import com.craftie.data.model.Beer

sealed class BeerDetailUiState {
    data class Success(val beer: Beer) : BeerDetailUiState()
    object Loading : BeerDetailUiState()
    object Error : BeerDetailUiState()
}