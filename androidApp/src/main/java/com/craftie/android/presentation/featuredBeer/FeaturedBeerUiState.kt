package com.craftie.android.presentation.featuredBeer

import com.craftie.data.model.Beer

sealed class FeaturedBeerUiState {
    data class Success(val beer: Beer) : FeaturedBeerUiState()
    object Error : FeaturedBeerUiState()
    object Loading : FeaturedBeerUiState()
}