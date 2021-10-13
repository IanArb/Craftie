package com.craftie.android.presentation.ratings

sealed class SendRatingUiState {
    data class Success(val message: String): SendRatingUiState()
    object Error : SendRatingUiState()
    object Loading : SendRatingUiState()
    object Idle : SendRatingUiState()
}