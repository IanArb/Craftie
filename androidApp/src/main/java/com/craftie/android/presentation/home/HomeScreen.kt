package com.craftie.android.presentation.home

import CraftieTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.craftie.android.util.MockData
import lightRed

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
        )
    ) {
        BeerFavourites()
        BeersTasted(MockData.provinces())
    }
}

@Composable
fun BeerFavourites() {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .clip(shape = CircleShape)
                        .background(lightRed)
                ) {
                    Image(
                        modifier = Modifier
                            .padding(12.dp),
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favourite"
                    )
                }
                Text(
                    text = "Your Favourites",
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                text = "You have no favourites added. Why not go ahead and add some?",
                modifier = Modifier.padding(top = 16.dp)
            )
        }

    }
}

@Composable
fun BeersTasted(provinces : List<MockData.Province>) {
    Text(
        text = "Beers Tasted",
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = 16.dp)
    )
    LazyRow(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(provinces) {
            Card(
                modifier = Modifier
                    .size(160.dp, 170.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
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
                            .size(75.dp)
                    )
                    Text(
                        text = "25%",
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(
                        text = it.name
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CraftieTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Home")
                    }
                )
            },
            bottomBar = {

            }
        ) {
            HomeScreen()
        }
    }
}