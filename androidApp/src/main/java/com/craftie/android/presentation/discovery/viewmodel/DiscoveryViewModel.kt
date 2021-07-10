package com.craftie.android.presentation.discovery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.presentation.discovery.model.DiscoveryUiState
import com.craftie.android.presentation.discovery.usecase.DiscoveryUseCase
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.android.util.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val dispatchers: CoroutinesDispatcherProvider,
    private val useCase: DiscoveryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DiscoveryUiState>(DiscoveryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun init() {
        viewModelScope.launch(dispatchers.io) {
            val data = useCase.build()

            data.collect {
                _uiState.value = it
            }
        }
    }
}