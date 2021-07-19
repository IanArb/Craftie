package com.craftie.android.presentation.beerByProvince

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.util.CoroutinesDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeersByProvinceViewModel @Inject constructor(
    private val beersByProvinceUseCase: BeersByProvinceUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<BeersByProvinceUiState>(BeersByProvinceUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun init() {
        viewModelScope.launch(dispatcherProvider.io) {
            val result = beersByProvinceUseCase.beersByProvince(savedStateHandle.get<String>("province") ?: "")

            result.collect {
                _uiState.value = it
            }
        }
    }


}