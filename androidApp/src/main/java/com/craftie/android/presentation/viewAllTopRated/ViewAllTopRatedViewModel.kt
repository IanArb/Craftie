package com.craftie.android.presentation.viewAllTopRated

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
class ViewAllTopRatedViewModel @Inject constructor(
    private val useCase: ViewAllTopRatedUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
): ViewModel() {

    private val _uiState = MutableStateFlow<ViewAllTopRatedUiState>(ViewAllTopRatedUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun init() {
        viewModelScope.launch(dispatcherProvider.io) {
            val result = useCase.beers()

            result.collect {
                _uiState.value = it
            }
        }
    }
}