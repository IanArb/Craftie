package com.craftie.android.presentation.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import lightBlack

@Composable
fun HomeScreenShimmer() {
    val color = if (isSystemInDarkTheme()) lightBlack else Color.LightGray

    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
        )
    ) {
        FavouritesHeader(showClearAll = false) {
            //noop
        }
        FavouritesCardShimmer(color)
        BeersFavouritesShimmer(color)
    }
}

@Composable
private fun FavouritesCardShimmer(color: Color) {
    LazyRow(
        modifier = Modifier
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(count = 3) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    BeerImageShimmer(color)
                }

                Spacer(Modifier.padding(4.dp))

                Text(
                    text = "Placeholder text",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .width(120.dp)
                        .padding(start = 2.dp)
                        .placeholder(
                            visible = true,
                            color = color,
                            highlight = PlaceholderHighlight.shimmer()
                        ),
                    maxLines = 2,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

@Composable
private fun BeerImageShimmer(color: Color) {
    Column(
        modifier = Modifier.padding(top = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .width(120.dp)
                .height(150.dp)
                .placeholder(
                    visible = true,
                    color = color,
                    highlight = PlaceholderHighlight.shimmer()
                ),
        )
    }
}

@Composable
fun BeersFavouritesShimmer(color: Color) {
    Text(
        text = "Favourites by province",
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
        items(count = 4) {
            Card(
                modifier = Modifier
                    .size(160.dp, 170.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .placeholder(
                                visible = true,
                                color = color,
                                highlight = PlaceholderHighlight.shimmer()
                            ),
                    )
                    Text(
                        text = "25%",
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .placeholder(
                                visible = true,
                                color = color,
                                highlight = PlaceholderHighlight.shimmer()
                            ),
                    )
                    Text(
                        text = "Placeholder text",
                        modifier = Modifier
                            .placeholder(
                                visible = true,
                                color = color,
                                highlight = PlaceholderHighlight.shimmer()
                            ),
                    )
                }
            }
        }
    }
}