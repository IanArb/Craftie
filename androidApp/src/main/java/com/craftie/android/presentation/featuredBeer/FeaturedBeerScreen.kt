package com.craftie.android.presentation.featuredBeer

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.craftie.android.presentation.components.*
import com.craftie.android.presentation.discovery.NoResultsCard

@Composable
fun FeaturedBeerScreen(popUp: () -> Unit) {
    val viewModel = hiltViewModel<FeaturedBeerViewModel>()

    viewModel.init()

    val uiState = viewModel.uiState.collectAsState()

    when (uiState.value) {
        is FeaturedBeerUiState.Success -> {
            val value = uiState.value as FeaturedBeerUiState.Success
            BeerDetail(value.beer) {
                popUp()
            }
        }
        is FeaturedBeerUiState.Error -> {
            ErrorView()
        }
        is FeaturedBeerUiState.Loading -> {
            CircularProgressBar()
        }
    }
}

@Composable
fun ErrorView() {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "")
        })
    }) {
        NoResultsCard {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeaturedBeer() {
    FeaturedBeerScreen {

    }
}

