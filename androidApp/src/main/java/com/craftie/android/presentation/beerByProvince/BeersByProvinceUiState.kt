package com.craftie.android.presentation.beerByProvince

import com.craftie.data.model.Beer

sealed class BeersByProvinceUiState {
    data class Success(val beers: List<Beer>) : BeersByProvinceUiState()
    object Empty : BeersByProvinceUiState()
    object Error : BeersByProvinceUiState()
    object Loading : BeersByProvinceUiState()
}