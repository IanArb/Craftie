package com.craftie.android.presentation.beerByProvince

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.craftie.android.presentation.components.BeersGrid
import com.craftie.android.presentation.components.CircularProgressBar
import com.craftie.android.presentation.discovery.NoResultsCard
import com.craftie.android.presentation.discovery.Spacer

@ExperimentalFoundationApi
@Composable
fun BeersByProvinceScreen() {
    val viewModel = hiltViewModel<BeersByProvinceViewModel>()

    viewModel.init()

    val uiState = viewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is BeersByProvinceUiState.Success -> {
            BeersGrid(state.beers)
        }

        is BeersByProvinceUiState.Empty -> {
            EmptyStateCard()
        }

        is BeersByProvinceUiState.Error -> {
            NoResultsCard {

            }
        }

        is BeersByProvinceUiState.Loading -> {
            CircularProgressBar()
        }
    }
}

@Composable
fun EmptyStateCard() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer()
                Text(
                    text = "No Beers Found",
                    fontWeight = FontWeight.Bold
                )
                Spacer()
                Text("There are no beers available in this province at this time. Please try another province.")
                Spacer()
            }
        }
    }
}