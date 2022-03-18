package com.craftie.android.presentation.beerByProvince

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.craftie.android.presentation.components.BeersGrid

@ExperimentalFoundationApi
@Composable
fun BeersByProvinceScreen() {
    val viewModel = hiltViewModel<BeersByProvinceViewModel>()

    val items = viewModel.pagingData.collectAsLazyPagingItems()

    BeersGrid(items)
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun BeersByProvincePreview() {
    BeersByProvinceScreen()
}