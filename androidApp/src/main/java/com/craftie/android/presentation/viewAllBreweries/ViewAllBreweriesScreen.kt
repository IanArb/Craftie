package com.craftie.android.presentation.viewAllBreweries

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.craftie.android.presentation.components.CircularProgressBar
import com.craftie.android.presentation.discovery.NoResultsCard
import com.craftie.data.model.Brewery
import com.google.accompanist.coil.rememberCoilPainter

@ExperimentalFoundationApi
@Composable
fun ViewAllBreweriesScreen() {

    val viewModel = hiltViewModel<ViewAllBreweriesViewModel>()

    viewModel.init()

    val state = viewModel.uiState.collectAsState()

    when (val value = state.value) {
        is ViewAllBreweriesUiState.Success -> {
            BreweryGrid(value.breweries)
        }
        is ViewAllBreweriesUiState.Error -> {
            NoResultsCard {

            }
        }
        is ViewAllBreweriesUiState.Loading -> {
            CircularProgressBar()
        }
    }
    
}

@ExperimentalFoundationApi
@Composable
fun BreweryGrid(breweries: List<Brewery>) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier,
            contentPadding = PaddingValues(12.dp)
        ) {
            items(breweries) { brewery ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Image(
                        painter = rememberCoilPainter(
                            brewery.imageUrl,
                            fadeIn = true
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(150.dp)
                    )

                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = brewery.name,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun PreviewViewAllBreweries() {
    ViewAllBreweriesScreen()
}