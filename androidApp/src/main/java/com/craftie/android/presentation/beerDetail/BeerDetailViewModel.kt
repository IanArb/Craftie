package com.craftie.android.presentation.beerDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.android.utils.Constants
import com.craftie.data.model.Beer
import com.craftie.data.repository.FavouritesRepository
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
    private val dispatcher: CoroutinesDispatcherProvider,
    private val favouritesRepository: FavouritesRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<BeerDetailUiState>(BeerDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun init() {
        viewModelScope.launch(dispatcher.io) {
            val id = savedStateHandle.get<String>(Constants.BEER_ID_KEY) ?: savedStateHandle.get<String>(Constants.SEARCH_RESULT_ID_KEY)
            val result = beerDetailUseCase.beer(id ?: "")

            result.collect {
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