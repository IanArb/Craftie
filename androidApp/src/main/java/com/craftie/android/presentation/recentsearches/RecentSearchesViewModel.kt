package com.craftie.android.presentation.recentsearches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.data.model.RecentSearchDb
import com.craftie.data.repository.RecentSearchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _recentSearches = MutableStateFlow(emptyList<RecentSearchDb>())
    val recentSearches = _recentSearches.asStateFlow()

    fun init() {
        viewModelScope.launch(dispatcherProvider.io) {
            recentSearchesRepository.findAllRecentSearches()
                .distinctUntilChanged()
                .collect {
                    _recentSearches.value = recentSearchesRepository.groupByDate(it)
                }
        }
    }

    fun addRecentSearch(id: String, name: String) = recentSearchesRepository.saveRecentSearch(id, name)

    fun removeRecentSearch(recentSearchDb: RecentSearchDb) = recentSearchesRepository.removeRecentSearch(recentSearchDb)

    fun removeAllRecentSearches() = recentSearchesRepository.removeAllRecentSearches()

}