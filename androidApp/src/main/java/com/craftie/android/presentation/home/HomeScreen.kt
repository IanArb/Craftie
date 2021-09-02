package com.craftie.android.presentation.home

import CraftieTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.craftie.android.util.MockData
import com.craftie.data.model.BeersDb
import lightGray
import lightRed

@Composable
fun HomeScreen(onClick: (Pair<String, String>) -> Unit) {
    val viewModel = hiltViewModel<HomeViewModel>()

    viewModel.fetchFavourites()

    val uiState = viewModel.favourites.collectAsState()

    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
        )
    ) {
        val beers = uiState.value
        if (beers.isEmpty()) {
            BeersFavouritesEmptyState()
        } else {
            FavouriteBeers(
                beers,
                onClick = {
                    onClick(it)
                },
                onClearAllClick = {
                    viewModel.removeAllBeers()
                },
                onRemoveBeerClick = {
                    viewModel.removeBeer(it)
                }
            )
        }
        BeersTasted(MockData.provinces())
    }
}

@Composable
fun BeersFavouritesEmptyState() {
    FavouritesHeader()

    Card(
        modifier = Modifier
            .padding(top = 10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "You have no favourites added. Why not go ahead and add some?",
                modifier = Modifier.padding(top = 16.dp)
            )
        }

    }
}

@Composable
private fun FavouritesHeader(
    showClearAll: Boolean = false,
    onClearAllClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
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
                contentDescription = "Favourite",
                colorFilter = ColorFilter.tint(Color.Red)
            )
        }
        Text(
            text = "Your Favourites",
            modifier = Modifier
                .padding(start = 6.dp)
                .align(Alignment.CenterVertically),
            fontWeight = FontWeight.Medium
        )

        if (showClearAll) {
            Text(
                modifier = Modifier
                    .padding(start = 120.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onClearAllClick?.invoke()
                    },
                text = "Clear All",
                fontWeight = FontWeight.Medium,
                color = Color.Blue
            )
        }
    }
}

@Composable
fun FavouriteBeers(
    beers: List<BeersDb>,
    onClick: (Pair<String, String>) -> Unit,
    onClearAllClick: () -> Unit,
    onRemoveBeerClick: (BeersDb) -> Unit
) {
    FavouritesHeader(showClearAll = true) {
        onClearAllClick()
    }
    LazyRow(
        modifier = Modifier
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(beers) {
            Card(
                modifier = Modifier
                    .height(250.dp)
                    .clickable {
                        val pair = Pair(it.id, it.name)
                        onClick(pair)
                    },
                shape = RoundedCornerShape(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(shape = CircleShape)
                            .background(lightGray)
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(6.dp)
                                .size(14.dp)
                                .clickable {
                                    onRemoveBeerClick(it)
                                },
                            imageVector = Icons.Default.Close,
                            contentDescription = it.name
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(top = 10.dp),
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
                            .padding(16.dp)
                            .width(120.dp)
                            .height(150.dp),
                        contentScale = ContentScale.Fit
                    )

                    Text(
                        text = it.name,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                    )
                }
            }
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
            HomeScreen {

            }
        }
    }
}