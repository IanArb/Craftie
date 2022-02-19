package com.craftie.android.presentation.viewAllBreweries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.craftie.data.model.Result
import com.craftie.data.repository.CraftieBreweriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ViewAllBreweriesViewModel @Inject constructor(
    breweriesRepository: CraftieBreweriesRepository,
): ViewModel() {

    val breweries = breweriesRepository.breweriesPagingData
}