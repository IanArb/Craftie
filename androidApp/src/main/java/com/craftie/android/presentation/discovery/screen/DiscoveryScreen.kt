package com.craftie.android.presentation.discovery.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.transform.RoundedCornersTransformation
import com.craftie.android.presentation.components.CircularProgressBar
import com.craftie.android.presentation.components.CollapsingToolbarScopeInstance.road
import com.craftie.android.presentation.components.gradientImageView
import com.craftie.android.presentation.discovery.model.DiscoveryUiData
import com.craftie.android.presentation.discovery.model.DiscoveryUiState
import com.craftie.android.presentation.discovery.viewmodel.DiscoveryViewModel
import com.craftie.android.util.MockData
import com.craftie.data.model.*
import com.google.accompanist.coil.rememberCoilPainter

@ExperimentalMaterialApi
@Composable
fun DiscoveryScreen(onFeaturedClick: () -> Unit) {
    val viewModel = hiltViewModel<DiscoveryViewModel>()

    viewModel.init()

    val uiState = viewModel.uiState.collectAsState()

    when (uiState.value) {
        is DiscoveryUiState.Success -> {
            val value = uiState.value as DiscoveryUiState.Success
            DiscoveryItems(uiData = value.uiData) {
                onFeaturedClick()
            }
        }
        is DiscoveryUiState.Error ->
            NoResultsCard {

            }

        is DiscoveryUiState.Loading -> CircularProgressBar()
    }
}

@ExperimentalMaterialApi
@Composable
fun DiscoveryItems(uiData: DiscoveryUiData, onFeaturedClick: () -> Unit) {
    val breweries = uiData.breweries
    val beers = uiData.beers

    val featuredBeer = uiData.beers.firstOrNull {
        it.isFeatured
    }
    LazyColumn(
        Modifier.padding(16.dp)
    ) {
        item {
            Breweries(breweries)
            Spacer(modifier = Modifier.padding(10.dp))
            featuredBeer?.let {
                Featured(it) {
                    onFeaturedClick()
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            TopRated(beers)
            Spacer(modifier = Modifier.padding(10.dp))
            Provinces(MockData.provinces())
            Spacer(modifier = Modifier.padding(24.dp))
        }
    }
}

@Composable
fun Breweries(breweries: List<Brewery>) {
    Header("Breweries Nearby")

    Spacer(modifier = Modifier.padding(10.dp))

    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(breweries) {
            Image(
                painter = rememberCoilPainter(
                    it.imageUrl,
                    fadeIn = true
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
            painter = rememberCoilPainter(
                request = featuredBeer.breweryInfo.brandImageUrl,
                fadeIn = true,
                requestBuilder = {
                    transformations(RoundedCornersTransformation(8.dp.value))
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .gradientImageView()
                .clickable {
                    onClick()
                }
        )

        Text(
            text = featuredBeer.name.uppercase(),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(60.dp, 16.dp, 16.dp, 16.dp)
                .width(175.dp)
                .align(Alignment.BottomStart),
            color = Color.White,
            fontSize = 16.sp,
            maxLines = 3
        )
    }
}

@Composable
fun TopRated(beers: List<Beer>) {
    Header(title = "Top Rated")
    Spacer(Modifier.padding(10.dp))
    val topRated = beers.take(3)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(topRated) {
            Card(
                modifier = Modifier.size(120.dp, 150.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Image(
                    painter = rememberCoilPainter(
                        it.imageUrl,
                        fadeIn = true
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun Provinces(provinces: List<String>) {
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
                painter = rememberCoilPainter(
                    it,
                    fadeIn = true
                ),
                contentDescription = null,
                modifier = Modifier.size(75.dp)
            )
        }
    }

}

@Composable
fun Header(title: String) {
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
                color = Color.Blue
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DiscoveryScreenPreview() {
    val data = DiscoveryUiData(
        MockData.breweries(),
        MockData.beers()
    )
    DiscoveryItems(uiData = data) {

    }
}