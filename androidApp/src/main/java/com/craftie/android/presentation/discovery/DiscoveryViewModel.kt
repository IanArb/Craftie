package com.craftie.android.presentation.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.craftie.android.util.CoroutinesDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.retry
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

            data
                .retry(3) { cause ->
                    if (cause is HttpException) {
                        (cause.response.code() == 401)
                    } else {
                        false
                    }
                }
                .collect {
                _uiState.value = it
            }
        }
    }
}