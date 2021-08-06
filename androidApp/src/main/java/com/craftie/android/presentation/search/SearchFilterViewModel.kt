package com.craftie.android.presentation.search

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
class SearchFilterViewModel @Inject constructor(
    private val searchFilterUseCase: SearchFilterUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
): ViewModel() {

    private val _uiState = MutableStateFlow<SearchFilterUiState>(SearchFilterUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun queryBeers(keyword: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            val result = searchFilterUseCase.findBeersByKeyword(keyword)

            result.collect {
                _uiState.value = it
            }
        }
    }
}