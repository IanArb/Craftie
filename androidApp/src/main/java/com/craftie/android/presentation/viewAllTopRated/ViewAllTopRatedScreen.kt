package com.craftie.android.presentation.viewAllTopRated

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.craftie.android.presentation.components.BeersGrid
import com.craftie.android.presentation.components.CircularProgressBar
import com.craftie.android.presentation.discovery.NoResultsCard

@ExperimentalFoundationApi
@Composable
fun ViewAllBeersScreen() {
    val viewModel = hiltViewModel<ViewAllTopRatedViewModel>()

    val items = viewModel.pagingData.collectAsLazyPagingItems()

    BeersGrid(items)
}