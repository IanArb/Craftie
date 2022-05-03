package com.craftie.android.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.craftie.android.util.CoroutinesDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    private val searchFilterUseCase: SearchFilterUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
): ViewModel() {

    private val _uiState = MutableStateFlow<SearchFilterUiState>(SearchFilterUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun queryBeers(keyword: Flow<String>) {
        viewModelScope.launch(dispatcherProvider.io) {
            keyword
                .distinctUntilChanged()
                .filter { it.length >= 3 }
                .retry(3) { cause ->
                    if (cause is HttpException) {
                        (cause.response.code() == 401)
                    } else {
                        false
                    }
                }
                .collect {
                    _uiState.value = SearchFilterUiState.Loading

                    val result = searchFilterUseCase.findBeersByKeyword(it)

                    result.collect { state ->
                        _uiState.value = state
                    }
                }
        }
    }
}