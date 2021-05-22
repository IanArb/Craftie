package com.craftie.android.ui.presentation.discovery.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.craftie.data.model.Brewery
import com.craftie.data.model.LatLng
import com.craftie.data.model.Location
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun DiscoveryScreen() {
    Column(
        Modifier.padding(16.dp)
    ) {
        Breweries(breweries())
        Featured()
        TopRated()
        Provinces()
    }
}

@Composable
fun Breweries(breweries: List<Brewery>) {
    Row {
        Row(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Text(text = "Breweries Nearby", Modifier.weight(0.8f), color = Color.Black)
            Text(text = "View All", color = Color.Blue)
        }
    }

    LazyRow {
        items(breweries) {
            Row {
                Image(
                    painter = rememberCoilPainter(it.imageUrl),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun Featured() {

}

@Composable
fun TopRated() {

}

@Composable
fun Provinces() {

}

@Composable
fun Newest() {

}

private fun breweries(): List<Brewery> {
    val fiveLamps = Brewery(
        "1",
        "Five Lamps",
        "Five Lamps",
        Location(
            "",
            "",
            "",
            LatLng(10.0, 10.0)
        ),
        "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/brewery%2F5_Lamps.png?alt=media&token=233fd82d-269a-4442-8ea0-6c2d2574f140",
        ""
    )
    val rascals = Brewery(
        "1",
        "Rascals",
        "Rascals",
        Location(
            "",
            "",
            "",
            LatLng(10.0, 10.0)
        ),
        "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/brewery%2FRascals.png?alt=media&token=ba9b7c9a-a279-465b-990a-f1f9c3f8c0fe",
        ""
    )
    val mcGargles = Brewery(
        "1",
        "McGargles",
        "McGargles",
        Location(
            "",
            "",
            "",
            LatLng(10.0, 10.0)
        ),
        "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/brewery%2FMcGargles.png?alt=media&token=23e25d26-6ace-4a1f-b55d-f176e46de6c4",
        ""
    )

    val wicklowWolf = Brewery(
        "1",
        "Wicklow Wolf",
        "Wicklow Wolf",
        Location(
            "",
            "",
            "",
            LatLng(10.0, 10.0)
        ),
        "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/brewery%2FWicklow_Wolf.png?alt=media&token=5ad3a72c-170e-4643-afbb-bb670434c0c8",
        ""
    )

    val obrother = Brewery(
        "1",
        "OBrother",
        "OBrother",
        Location(
            "",
            "",
            "",
            LatLng(10.0, 10.0)
        ),
        "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/brewery%2FOBrother.png?alt=media&token=63763651-c86a-4427-9cff-d041a9334474",
        ""
    )

    return listOf(rascals, obrother, mcGargles, fiveLamps, wicklowWolf)
}