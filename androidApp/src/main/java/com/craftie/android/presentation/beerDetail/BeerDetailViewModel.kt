package com.craftie.android.presentation.beerDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.android.util.Constants
import com.craftie.data.model.Beer
import com.craftie.data.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    private val beerDetailUseCase: BeerDetailUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val dispatcher: CoroutinesDispatcherProvider,
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BeerDetailUiState>(BeerDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun init() {
        viewModelScope.launch(dispatcher.io) {
            val beerId = savedStateHandle.get<String>(Constants.BEER_ID_KEY) ?: ""
            val searchResultId = savedStateHandle.get<String>(Constants.SEARCH_RESULT_ID_KEY) ?: ""
            val id = beerId.ifEmpty {
                searchResultId
            }

            val result = beerDetailUseCase.beer(id)

            result
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

    fun saveBeer(beer: Beer) {
        viewModelScope.launch(dispatcher.io) {
            favouritesRepository.saveBeer(beer)
        }
    }
}