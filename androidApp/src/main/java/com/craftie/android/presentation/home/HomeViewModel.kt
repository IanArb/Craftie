package com.craftie.android.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.data.model.BeersDb
import com.craftie.data.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider
): ViewModel() {

    private val _favourites = MutableStateFlow<List<BeersDb>>(emptyList())
    val favourites = _favourites.asStateFlow()

    fun fetchFavourites() {
        viewModelScope.launch(dispatcherProvider.io) {
            favouritesRepository.findAllBeers()
                .distinctUntilChanged()
                .collect {
                    _favourites.value = it.query()
                }
        }
    }

    fun removeBeer(beer: BeersDb) {
        favouritesRepository.removeBeer(beer)
    }

    fun removeAllBeers() {
        favouritesRepository.removeAll()
    }
}