package com.craftie.android.presentation.viewAllBreweries

import com.craftie.data.model.Brewery

sealed class ViewAllBreweriesUiState {
    data class Success(val breweries: List<Brewery>) : ViewAllBreweriesUiState()
    object Error : ViewAllBreweriesUiState()
    object Loading : ViewAllBreweriesUiState()
}