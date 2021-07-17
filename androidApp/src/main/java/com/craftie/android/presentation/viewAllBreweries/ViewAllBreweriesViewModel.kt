package com.craftie.android.presentation.viewAllBreweries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.data.repository.CraftieBreweriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAllBreweriesViewModel @Inject constructor(
    private val dispatchers: CoroutinesDispatcherProvider,
    private val viewAllBreweriesUseCase: ViewAllBreweriesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<ViewAllBreweriesUiState>(ViewAllBreweriesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun init() {
        viewModelScope.launch(dispatchers.io) {
            viewAllBreweriesUseCase.breweries().collect {
                _uiState.value = it
            }
        }
    }

}