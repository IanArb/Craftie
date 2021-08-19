package com.craftie.android.presentation.discovery

import com.craftie.data.model.Beer
import com.craftie.data.model.Brewery
import com.craftie.data.model.Province

data class DiscoveryUiData(
    val breweries: List<Brewery>,
    val beers: List<Beer>,
    val provinces: List<Province>
)