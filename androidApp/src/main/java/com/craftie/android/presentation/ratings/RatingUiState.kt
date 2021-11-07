package com.craftie.android.presentation.ratings

import com.craftie.data.model.RatingResult

sealed class RatingUiState {
    data class Success(val rating: RatingResult) : RatingUiState()
    object Error : RatingUiState()
    object Loading : RatingUiState()
}