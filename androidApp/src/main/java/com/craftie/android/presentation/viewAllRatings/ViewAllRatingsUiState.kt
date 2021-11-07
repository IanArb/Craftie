package com.craftie.android.presentation.viewAllRatings

import com.craftie.data.model.RatingResponse


sealed class ViewAllRatingsUiState {
    data class Success(val ratings: List<RatingResponse>) : ViewAllRatingsUiState()
    object Empty : ViewAllRatingsUiState()
    object Error : ViewAllRatingsUiState()
    object Loading : ViewAllRatingsUiState()
}