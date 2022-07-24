package com.craftie.android.presentation.viewAllTopRated

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.craftie.android.presentation.components.BeersGrid

@ExperimentalFoundationApi
@Composable
fun ViewAllBeersScreen(viewModel: ViewAllTopRatedViewModel = hiltViewModel()) {
    val items = viewModel.pagingData.collectAsLazyPagingItems()

    BeersGrid(items)
}