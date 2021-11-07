package com.craftie.android.presentation.ratings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.android.utils.Constants
import com.craftie.data.model.RatingRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveBeerRatingViewModel @Inject constructor(
    private val saveBeerRatingUseCase: SaveBeerRatingUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val dispatchers: CoroutinesDispatcherProvider
): ViewModel() {

    private val _sendRatingState = MutableStateFlow<SendRatingUiState>(SendRatingUiState.Idle)
    val sendRatingUiState = _sendRatingState.asStateFlow()

    private val _ratingUiState = MutableStateFlow<RatingUiState>(RatingUiState.Loading)
    val ratingUiState = _ratingUiState.asStateFlow()

    fun sendRating(ratingRequest: RatingRequest) {
        val id = savedStateHandle.get<String>(Constants.BEER_ID_KEY) ?: savedStateHandle.get<String>(
            Constants.SEARCH_RESULT_ID_KEY) ?: ""
        _sendRatingState.value = SendRatingUiState.Loading
        viewModelScope.launch(dispatchers.io) {
            val result = saveBeerRatingUseCase.saveRating(ratingRequest.copy(beerId = id))

            result.collect {
                _sendRatingState.value = it
            }
        }
    }

    fun fetchRating() {
        val id = savedStateHandle.get<String>(Constants.BEER_ID_KEY) ?: savedStateHandle.get<String>(
            Constants.SEARCH_RESULT_ID_KEY) ?: ""

        viewModelScope.launch(dispatchers.io) {
            val result = saveBeerRatingUseCase.ratingByBeerId(id)
            result.collect {
                _ratingUiState.value = it
            }
        }
    }

}