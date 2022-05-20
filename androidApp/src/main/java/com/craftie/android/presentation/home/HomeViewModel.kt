package com.craftie.android.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.authentication.TokenUseCase
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.data.model.FavouriteBeerUiData
import com.craftie.data.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository,
    private val tokenUseCase: TokenUseCase,
    private val favouriteBeersUseCase: FavouriteBeersUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
): ViewModel() {

    private val _favourites = MutableStateFlow<List<FavouriteBeerUiData>>(emptyList())
    val favourites = _favourites.asStateFlow()

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Loading)
    val loginUiState = _loginUiState.asStateFlow()

    private val _beersFavouritesUiState = MutableStateFlow<FavouriteBeersUiState>(FavouriteBeersUiState.Loading)
    val beersFavourites = _beersFavouritesUiState.asStateFlow()

    fun fetchFavourites() {
        viewModelScope.launch(dispatcherProvider.io) {
            favouritesRepository.findAllBeers()
                .distinctUntilChanged()
                .collect {
                    _favourites.value = it
                }
        }
    }

    fun removeBeer(id: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            favouritesRepository.removeBeer(id)
        }
    }

    fun removeAllBeers() {
        viewModelScope.launch(dispatcherProvider.io) {
            favouritesRepository.removeAll()
        }
    }

    fun login() {
        viewModelScope.launch {
            tokenUseCase.login()
                .collect {
                    _loginUiState.value = it
                }
        }
    }

    fun fetchBeersTasted(favourites: List<FavouriteBeerUiData>) {
        viewModelScope.launch(dispatcherProvider.io) {
            favouriteBeersUseCase.fetchFavouriteBeersByProvince(favourites)
                .collect {
                    _beersFavouritesUiState.value = it
                }
        }
    }
}