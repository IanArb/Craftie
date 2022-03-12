package com.craftie.android.presentation.discovery

import CraftieTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.parseGuideline
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.craftie.android.presentation.components.CircularProgressBar
import com.craftie.android.presentation.components.gradientImageView
import com.craftie.android.util.MockData
import com.craftie.data.model.*
import com.google.accompanist.pager.HorizontalPager

@ExperimentalMaterialApi
@Composable
fun DiscoveryScreen(
    onFeaturedClick: () -> Unit,
    onViewAllBreweriesClick: () -> Unit,
    onViewAllBeersClick: () -> Unit,
    onProvinceClick: (String) -> Unit,
    onTopRatedBeerClick: (String) -> Unit,
    onNewBeerClick: (String) -> Unit
) {
    val viewModel = hiltViewModel<DiscoveryViewModel>()

    viewModel.init()

    val uiState = viewModel.uiState.collectAsState()

    when (val value = uiState.value) {
        is DiscoveryUiState.Success -> {
            DiscoveryItems(
                uiData = value.uiData,
                onFeaturedClick = { onFeaturedClick() },
                onViewAllBreweriesClick = { onViewAllBreweriesClick() },
                onViewAllBeersClick = { onViewAllBeersClick() },
                onProvinceClick = { onProvinceClick(it) },
                onTopRatedBeerClick = { onTopRatedBeerClick(it) },
                onNewBeerClick = { onNewBeerClick(it) }
            )
        }
        is DiscoveryUiState.Error ->
            NoResultsCard {

            }

        is DiscoveryUiState.Loading -> CircularProgressBar()
    }
}

@ExperimentalMaterialApi
@Composable
fun DiscoveryItems(
    uiData: DiscoveryUiData,
    onFeaturedClick: () -> Unit,
    onViewAllBreweriesClick: () -> Unit,
    onViewAllBeersClick: () -> Unit,
    onProvinceClick: (String) -> Unit,
    onTopRatedBeerClick: (String) -> Unit,
    onNewBeerClick: (String) -> Unit
) {
    val breweries = uiData.breweries
    val beers = uiData.beers
    val provinces = uiData.provinces

    val featuredBeer = uiData.featuredBeer

    LazyColumn(
        Modifier.padding(16.dp)
    ) {
        item {
            Breweries(breweries) {
                onViewAllBreweriesClick()
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Featured(featuredBeer) {
                onFeaturedClick()
            }
            Spacer(modifier = Modifier.padding(10.dp))
            TopRated(beers = beers,
                onViewAllBeersClick = {
                    onViewAllBeersClick()
                },
                onBeerClick = {
                    onTopRatedBeerClick(it)
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Provinces(provinces) {
                onProvinceClick(it)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            NewBeers(beers = beers) {
                onNewBeerClick(it)
            }
            Spacer(modifier = Modifier.padding(30.dp))
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Breweries(breweries: List<Brewery>, onViewAllBreweriesClick: () -> Unit) {
    Header("Breweries Nearby") {
        onViewAllBreweriesClick()
    }

    Spacer(modifier = Modifier.padding(10.dp))

    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(breweries) {
            Image(
                painter = rememberImagePainter(
                    data = it.imageUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Featured(featuredBeer: Beer, onClick: () -> Unit) {
    Text("Featured", fontWeight = FontWeight.Bold)

    Spacer(modifier = Modifier.padding(10.dp))

    Box {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .gradientImageView()
                .fillMaxWidth()
                .height(200.dp)
                .clickable {
                    onClick()
                },
            painter = rememberImagePainter(
                data = featuredBeer.breweryInfo.brandImageUrl,
                builder = {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(8.dp.value))
                },
            ),
            contentDescription = null
        )

        Text(
            text = featuredBeer.name.uppercase(),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
                .width(175.dp)
                .align(Alignment.BottomStart),
            color = Color.White,
            fontSize = 16.sp,
            maxLines = 3
        )
    }
}

@Composable
fun TopRated(
    beers: List<Beer>,
    onViewAllBeersClick: () -> Unit,
    onBeerClick: (String) -> Unit
) {
    Header(title = "Top Rated") {
        onViewAllBeersClick()
    }
    Spacer(Modifier.padding(10.dp))
    BeersHorizontalGrid(beers = beers) {
        onBeerClick(it)
    }
}

@Composable
fun BeersHorizontalGrid(
    beers: List<Beer>,
    onBeerClick: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(beers) {
            Column {
                Card(
                    modifier = Modifier.size(120.dp, 150.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = it.imageUrl,
                            builder = {
                                crossfade(true)
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clickable {
                                onBeerClick(it.id)
                            },
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(Modifier.padding(4.dp))

                Text(
                    text = it?.name ?: "",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .width(120.dp)
                        .padding(start = 2.dp),
                    maxLines = 2,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

@Composable
fun Provinces(
    provinces: List<Province>,
    onProvinceClick: (String) -> Unit) {
    Text(
        "By Province",
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.padding(10.dp))
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(provinces) {
            Image(
                painter = rememberImagePainter(
                    data = it.imageUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(75.dp)
                    .clickable {
                        onProvinceClick(it.name)
                    }
            )
        }
    }

}

@Composable
fun NewBeers(
    beers: List<Beer>,
    onNewBeerClick: (String) -> Unit
) {
    Text(
        "Newest",
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.padding(10.dp))
    BeersHorizontalGrid(beers) {
        onNewBeerClick(it)
    }
}

@Composable
fun Header(title: String, onViewAllClick: () -> Unit) {
    Row {
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "View All",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    onViewAllClick()
                }
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DiscoveryScreenPreview() {
    CraftieTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Discovery")
                    }
                )
            }
        ) {
            val data = DiscoveryUiData(
                MockData.breweries(),
                MockData.beers(),
                MockData.provinces(),
                MockData.beers().first()
            )
            DiscoveryItems(
                uiData = data,
                onFeaturedClick = {},
                onViewAllBreweriesClick = {},
                onViewAllBeersClick = {},
                onProvinceClick = {},
                onTopRatedBeerClick = {},
                onNewBeerClick = {}
            )
        }
    }
}