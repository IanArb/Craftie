package com.craftie.android.presentation.featuredBeer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.presentation.featuredBeer.FeaturedBeerUiState
import com.craftie.android.presentation.featuredBeer.FeaturedBeerUseCase
import com.craftie.android.util.CoroutinesDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeaturedBeerViewModel @Inject constructor(
    private val featuredBeerUseCase: FeaturedBeerUseCase,
    private val dispatchers: CoroutinesDispatcherProvider
) : ViewModel()  {

    private val _uiState = MutableStateFlow<FeaturedBeerUiState>(FeaturedBeerUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun init() {
        viewModelScope.launch(dispatchers.io) {
            val result = featuredBeerUseCase.featuredBeer()

            result.collect {
                _uiState.value = it
            }
        }
    }
}