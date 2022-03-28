package com.craftie.android.presentation.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.presentation.discovery.DiscoveryUiState
import com.craftie.android.presentation.discovery.DiscoveryUseCase
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.data.model.Beer
import com.craftie.data.useCase.CraftieFilterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val dispatchers: CoroutinesDispatcherProvider,
    private val useCase: DiscoveryUseCase,
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