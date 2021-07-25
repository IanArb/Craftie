package com.craftie.android.presentation.beerDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.android.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    private val beerDetailUseCase: BeerDetailUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val dispatcher: CoroutinesDispatcherProvider
): ViewModel() {

    private val _uiState = MutableStateFlow<BeerDetailUiState>(BeerDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun init() {
        viewModelScope.launch(dispatcher.io) {
            val result = beerDetailUseCase.beer(savedStateHandle.get<String>(Constants.BEER_ID_KEY) ?: "")

            result.collect {
                _uiState.value = it
            }
        }
    }
}