package com.craftie.android.presentation.viewAllTopRated

import com.craftie.data.model.Beer

sealed class ViewAllTopRatedUiState {
    data class Success(val beers: List<Beer>) : ViewAllTopRatedUiState()
    object Error : ViewAllTopRatedUiState()
    object Loading : ViewAllTopRatedUiState()
}