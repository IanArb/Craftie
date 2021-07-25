package com.craftie.android.presentation.beerDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.craftie.android.presentation.components.BeerDetail
import com.craftie.android.presentation.components.CircularProgressBar
import com.craftie.android.presentation.discovery.NoResultsCard

@Composable
fun BeerDetailScreen(popUp: () -> Unit) {
    val viewModel = hiltViewModel<BeerDetailViewModel>()

    viewModel.init()

    val state = viewModel.uiState.collectAsState()

    when (val uiState = state.value) {
        is BeerDetailUiState.Success -> {
            BeerDetail(beer = uiState.beer) {
                popUp()
            }
        }
        is BeerDetailUiState.Error -> {
            NoResultsCard {

            }
        }
        is BeerDetailUiState.Loading -> {
            CircularProgressBar()
        }
    }
}