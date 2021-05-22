package com.craftie.android.ui.presentation.discovery.model

import com.craftie.data.model.Beer
import com.craftie.data.model.Brewery

data class DiscoveryUiData(
    val breweries: List<Brewery>,
    val beers: List<Beer>
)