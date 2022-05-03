package com.craftie.android.presentation.viewAllRatings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.android.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAllRatingsViewModel @Inject constructor(
    private val viewAllRatingsUseCase: ViewAllRatingsUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewAllRatingsUiState>(ViewAllRatingsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchRatings() {
        viewModelScope.launch(dispatcherProvider.io) {
            val id = savedStateHandle.get<String>(Constants.BEER_ID_KEY) ?: ""
            viewAllRatingsUseCase.build(id)
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