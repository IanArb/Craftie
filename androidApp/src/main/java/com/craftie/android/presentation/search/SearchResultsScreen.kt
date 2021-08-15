package com.craftie.android.presentation.search

import CraftieTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.craftie.android.util.MockData
import com.craftie.data.model.Beer
import com.craftie.android.R
import com.craftie.android.presentation.components.ratingBar.RatingBar

@ExperimentalMaterialApi
@Composable
fun SearchResults(
    beers: List<Beer>,
    onClick: (Pair<String, String>) -> Unit
) {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 80.dp
        )
    ) {
        LazyColumn {
            items(beers) {
                ResultsCard(beer = it) { id ->
                    onClick(id)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ResultsCard(
    beer: Beer,
    onClick: (Pair<String, String>) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        elevation = 2.dp,
        onClick = {
            onClick(Pair(beer.id, beer.name))
        }
    ) {
        val imageUrl = if (beer.imageUrl == "{placeholder}") {
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2Fmammoth.png?alt=media&token=b6c970db-3a54-41fb-b4a6-72d7cc6a3ba3"
        } else {
            beer.imageUrl
        }
        Row {
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .width(100.dp)
                    .height(150.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier.padding(
                    top = 26.dp
                )
            ) {
                Text(
                    text = beer.style,
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
                Spacer(Modifier.padding(1.dp))
                Text(
                    text = beer.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(Modifier.padding(1.dp))
                Text(
                    text = beer.breweryInfo.name,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Spacer(Modifier.padding(8.dp))
                val rating: Float by rememberSaveable { mutableStateOf(4.2f) }
                RatingBar(value = rating, isIndicator = true, onRatingChanged = {

                })
                Spacer(Modifier.padding(4.dp))
                Text(
                    text = "Based on 300 reviews",
                    fontSize = 10.sp,
                    color = Color.DarkGray
                )
            }
        }

    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun SearchResultsPreview() {
    CraftieTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Result")
                    }
                )
            }
        ) {
            SearchResults(beers = MockData.beers()) {

            }
        }

    }
}