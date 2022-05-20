package com.craftie.android.presentation.home

sealed class FavouriteBeersUiState {
    data class Success(val value: List<FavouriteBeersUiData>) : FavouriteBeersUiState()
    object Error : FavouriteBeersUiState()
    object Loading : FavouriteBeersUiState()
}