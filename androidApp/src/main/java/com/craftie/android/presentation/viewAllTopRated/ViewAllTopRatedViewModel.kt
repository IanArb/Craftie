package com.craftie.android.presentation.viewAllTopRated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.data.repository.CraftieBeersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAllTopRatedViewModel @Inject constructor(
    beersRepository: CraftieBeersRepository,
): ViewModel() {

    val pagingData = beersRepository.beersPagingData
}