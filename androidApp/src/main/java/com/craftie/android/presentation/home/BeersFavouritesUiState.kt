package com.craftie.android.presentation.home

sealed class BeersFavouritesUiState {
    data class Success(val value: List<BeersFavouritesUi>) : BeersFavouritesUiState()
    object Error : BeersFavouritesUiState()
    object Loading : BeersFavouritesUiState()
}