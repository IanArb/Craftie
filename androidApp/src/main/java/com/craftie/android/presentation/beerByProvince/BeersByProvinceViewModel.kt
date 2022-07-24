package com.craftie.android.presentation.beerByProvince

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.craftie.android.util.Constants
import com.craftie.data.repository.CraftieBeersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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