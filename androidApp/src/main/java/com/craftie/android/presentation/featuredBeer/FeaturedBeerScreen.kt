package com.craftie.android.presentation.featuredBeer

import CraftieTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.craftie.android.presentation.components.*
import com.craftie.android.presentation.components.NoResultsCard
import com.craftie.android.util.MockData

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
            ErrorView {
                viewModel.init()
            }
        }
        is FeaturedBeerUiState.Loading -> {
            FeaturedBeerShimmerScaffold {
                popUp()
            }
        }
    }
}

@Composable
fun ErrorView(action: () -> Unit) {
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
    CraftieTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Featured")
                    }
                )
            }
        ) {
            BeerDetail(MockData.beers().first()) {

            }
        }
    }
}

