package com.craftie.android.ui.presentation.discovery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.ui.presentation.discovery.model.DiscoveryUiState
import com.craftie.android.ui.presentation.discovery.usecase.DiscoveryUseCase
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.utils.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    dispatchers: CoroutinesDispatcherProvider,
    private val useCase: DiscoveryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiscoveryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value = DiscoveryUiState(isLoading = true)

        viewModelScope.launch(dispatchers.io) {
            val data = useCase.build()

            data.collect {
                if (it is Outcome.Success) {
                    _uiState.value = DiscoveryUiState(uiData = it.value)
                } else {
                    _uiState.value = DiscoveryUiState(isError = true)
                }
            }
        }
    }
}