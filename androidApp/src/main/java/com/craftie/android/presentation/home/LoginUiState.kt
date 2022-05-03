package com.craftie.android.presentation.home

sealed class LoginUiState {
    object Success : LoginUiState()
    object Error : LoginUiState()
    object Loading : LoginUiState()
}