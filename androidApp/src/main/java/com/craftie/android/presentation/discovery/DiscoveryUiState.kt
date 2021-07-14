package com.craftie.android.presentation.discovery

sealed class DiscoveryUiState {
    data class Success(val uiData: DiscoveryUiData) : DiscoveryUiState()
    object Loading : DiscoveryUiState()
    object Error : DiscoveryUiState()
}