package com.craftie.android.ui.presentation.discovery.model

data class DiscoveryUiState(
    val isLoading: Boolean= false,
    val isError: Boolean = false,
    val uiData: DiscoveryUiData? = null
)