package com.craftie.android.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.authentication.TokenUseCase
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.data.model.BeersDb
import com.craftie.data.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.notifications.InitialResults
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository,
    private val tokenUseCase: TokenUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
): ViewModel() {

    private val _favourites = MutableStateFlow<List<BeersDb>>(emptyList())
    val favourites = _favourites.asStateFlow()

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Loading)
    val loginUiState = _loginUiState.asStateFlow()

    fun fetchFavourites() {
        viewModelScope.launch(dispatcherProvider.io) {
            favouritesRepository.findAllBeers()
                .distinctUntilChanged()
                .collect {
                    when (it) {
                        is InitialResults -> _favourites.value = it.list
                        else -> _favourites.value = it.list
                    }
                }
        }
    }

    fun removeBeer(beer: BeersDb) {
        favouritesRepository.removeBeer(beer)
    }

    fun removeAllBeers() {
        favouritesRepository.removeAll()
    }

    fun login() {
        viewModelScope.launch {
            tokenUseCase.login()
                .collect {
                    _loginUiState.value = it
                }
        }
    }
}