package com.craftie.android.presentation.recentsearches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.data.model.RecentSearchDb
import com.craftie.data.model.RecentSearchUiData
import com.craftie.data.repository.RecentSearchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.notifications.InitialResults
import io.realm.notifications.ResultsChange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentSearchesViewModel @Inject constructor(
    private val recentSearchesRepository: RecentSearchesRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider
): ViewModel() {

    private val _recentSearches = MutableStateFlow(emptyList<RecentSearchUiData>())
    val recentSearches = _recentSearches.asStateFlow()

    fun init() {
        viewModelScope.launch(dispatcherProvider.io) {
            recentSearchesRepository.findAllRecentSearches()
                .distinctUntilChanged()
                .collect {
                    _recentSearches.value = it
                }
        }
    }

    suspend fun addRecentSearch(id: String, name: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            recentSearchesRepository.saveRecentSearch(id, name)
        }
    }

    suspend fun removeRecentSearch(id: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            recentSearchesRepository.removeRecentSearch(id)
        }
    }

    suspend fun removeAllRecentSearches() {
        viewModelScope.launch(dispatcherProvider.io) {
            recentSearchesRepository.removeAllRecentSearches()
        }
    }

}