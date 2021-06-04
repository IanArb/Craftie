package com.craftie.android.presentation.discovery.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.craftie.android.presentation.components.CircularProgressBar
import com.craftie.android.presentation.discovery.model.DiscoveryUiData
import com.craftie.android.presentation.discovery.viewmodel.DiscoveryViewModel
import com.craftie.android.util.MockData
import com.craftie.data.model.*
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun DiscoveryScreen() {
    val viewModel = hiltViewModel<DiscoveryViewModel>()

    viewModel.init()

    val uiState = viewModel.uiState.collectAsState()

    with(uiState.value) {
        if (isLoading) {
            CircularProgressBar()
        }

        if (isError) {
            NoResultsCard {

            }
        }

        if (uiData != null) {
            DiscoveryItems(uiData = uiData)
        }
    }
}

@Composable
fun DiscoveryItems(uiData: DiscoveryUiData) {
    val breweries = uiData.breweries
    val beers = uiData.beers
    LazyColumn(
        Modifier.padding(16.dp)
    ) {
        item {
            Breweries(breweries)
            Spacer(modifier = Modifier.padding(10.dp))
            Featured()
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
                painter = rememberCoilPainter(it.imageUrl),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
        }
    }
}

@Composable
fun Featured() {
    Text("Featured")

    Spacer(Modifier.padding(10.dp))

    Card {
        Image(
            painter = rememberCoilPainter(request = "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/brewery_brand%2FWicklow%20Wolf.png?alt=media&token=9d1713cb-1c20-45ff-b6f9-cf4e037bcbf1"),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TopRated(beers: List<Beer>) {
    Header(title = "Top Rated")
    Spacer(Modifier.padding(10.dp))
    val topRated = beers.take(3)
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(topRated) {
            Image(
                painter = rememberCoilPainter(it.imageUrl),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
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
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(provinces) {
                Image(
                    painter = rememberCoilPainter(it),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
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

@Preview(showBackground = true)
@Composable
fun DiscoveryScreenPreview() {
    val data = DiscoveryUiData(
        MockData.breweries(),
        MockData.beers()
    )
    DiscoveryItems(uiData = data)
}