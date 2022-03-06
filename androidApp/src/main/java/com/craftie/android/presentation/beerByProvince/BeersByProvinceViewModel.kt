package com.craftie.android.presentation.beerByProvince

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.android.utils.Constants
import com.craftie.data.repository.CraftieBeersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeersByProvinceViewModel @Inject constructor(
    beersRepository: CraftieBeersRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val pagingData = beersRepository.beersProvincesData(
        savedStateHandle.get<String>(
            Constants.PROVINCE_KEY
        ) ?: ""
    )

}