package com.craftie.android.presentation.viewAllTopRated

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.craftie.android.presentation.components.BeersGrid
import com.craftie.android.presentation.components.CircularProgressBar
import com.craftie.android.presentation.discovery.NoResultsCard

@ExperimentalFoundationApi
@Composable
fun ViewAllBeersScreen() {
    val viewModel = hiltViewModel<ViewAllTopRatedViewModel>()

    viewModel.init()

    val state = viewModel.uiState.collectAsState()

    when (val value = state.value) {
        is ViewAllTopRatedUiState.Success -> {
            BeersGrid(value.beers)
        }
        is ViewAllTopRatedUiState.Error -> {
            NoResultsCard {

            }
        }
        is ViewAllTopRatedUiState.Loading -> {
            CircularProgressBar()
        }
    }

}