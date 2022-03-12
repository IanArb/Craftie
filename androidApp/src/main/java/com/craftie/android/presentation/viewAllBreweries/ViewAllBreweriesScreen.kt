package com.craftie.android.presentation.viewAllBreweries

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.craftie.android.R
import com.craftie.android.presentation.discovery.NoResultsCard
import com.craftie.data.model.Brewery

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun ViewAllBreweriesScreen() {
    val viewModel = hiltViewModel<ViewAllBreweriesViewModel>()

    val items = viewModel.breweries.collectAsLazyPagingItems()

    BreweryGrid(breweries = items)
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun BreweryGrid(breweries: LazyPagingItems<Brewery>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier,
            contentPadding = PaddingValues(12.dp)
        ) {
            items(breweries.itemCount) { index ->
                breweries[index]?.let {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = it.imageUrl,
                                builder = {
                                    crossfade(true)
                                    error(R.drawable.ic_photo_black_24dp)
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp)
                        )

                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = it.name,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        breweries.apply {
            when {
                loadState.refresh is LoadState.Error -> {
                    NoResultsCard {

                    }
                }
                loadState.append is LoadState.Error -> {
                    NoResultsCard {

                    }
                }
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun PreviewViewAllBreweries() {
    ViewAllBreweriesScreen()
}