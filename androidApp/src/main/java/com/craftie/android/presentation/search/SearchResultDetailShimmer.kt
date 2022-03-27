package com.craftie.android.presentation.search

import CraftieTheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.craftie.android.presentation.components.ratingBar.RatingBar
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import lightBlack

@Composable
fun SearchResultDetailShimmer() {
    LazyColumn {
        item {
            DetailShimmer()
            Spacer(modifier = Modifier.padding(10.dp))
            BeerDescriptionShimmer()
            Spacer(Modifier.padding(10.dp))
            BreweryDetailShimmer()
        }
    }
}

@Composable
fun DetailShimmer() {
    val color = if (isSystemInDarkTheme()) lightBlack else Color.LightGray

    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .placeholder(
                    visible = true,
                    color = color,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )

        Card(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .align(alignment = Alignment.BottomEnd)
                .offset(y = (200).dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 10.dp
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val (icon, favourite) = createRefs()
                        Box(
                            modifier = Modifier
                                .padding(12.dp)
                                .clip(shape = RoundedCornerShape(4.dp))
                                .constrainAs(icon) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                }
                                .placeholder(
                                    visible = true,
                                    color = color,
                                    highlight = PlaceholderHighlight.shimmer()
                                )
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        start = 10.dp,
                                        end = 10.dp,
                                        top = 6.dp,
                                        bottom = 6.dp
                                    )
                                    .placeholder(
                                        visible = true,
                                        color = color,
                                        highlight = PlaceholderHighlight.shimmer()
                                    ),
                                text = "Text to load content",
                                color = Color.Black,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(top = 6.dp, end = 72.dp)
                                .clip(shape = CircleShape)
                                .constrainAs(favourite) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.end)
                                    end.linkTo(parent.end)
                                }
                                .placeholder(
                                    visible = true,
                                    color = color,
                                    highlight = PlaceholderHighlight.shimmer()
                                )
                        ) {

                            Box(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .placeholder(
                                        visible = true,
                                        color = color,
                                        highlight = PlaceholderHighlight.shimmer()
                                    )
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.padding(2.dp))
                    Text(
                        fontSize = 12.sp,
                        text = "Text to load content",
                        modifier = Modifier
                            .placeholder(
                                visible = true,
                                color = color,
                                highlight = PlaceholderHighlight.shimmer()
                            )
                    )
                    Spacer(Modifier.padding(1.dp))
                    Text(
                        text = "Text to load content",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .placeholder(
                                visible = true,
                                color = color,
                                highlight = PlaceholderHighlight.shimmer()
                            )
                    )
                    Spacer(Modifier.padding(2.dp))
                    RatingBar(
                        value = 0f,
                        isIndicator = true,
                        modifier = Modifier
                            .placeholder(
                                visible = true,
                                color = color,
                                highlight = PlaceholderHighlight.shimmer()
                            ),
                        onRatingChanged = {

                        })
                    Spacer(Modifier.padding(2.dp))
                    Text(
                        text = "Based on 20 reviews",
                        fontSize = 12.sp,
                        color = Color.Blue,
                        modifier = Modifier
                            .placeholder(
                                visible = true,
                                color = color,
                                highlight = PlaceholderHighlight.shimmer()
                            )
                    )
                    Spacer(Modifier.padding(10.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 12.dp,
                                end = 12.dp
                            )
                            .placeholder(
                                visible = true,
                                color = color,
                                highlight = PlaceholderHighlight.shimmer()
                            ),
                        onClick = {

                        }
                    ) {
                        Text(
                            text = "Button",
                            fontSize = 16.sp,
                        )
                    }
                    Spacer(Modifier.padding(10.dp))
                }

            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
                .align(alignment = Alignment.BottomEnd)
                .offset(y = 16.dp)
                .placeholder(
                    visible = true,
                    color = color,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )
    }

}

@Composable
fun BeerDescriptionShimmer() {
    val color = if (isSystemInDarkTheme()) lightBlack else Color.LightGray

    Card(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 200.dp
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 10.dp
            )
        ) {
            Text(
                text = "Description",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier
                    .placeholder(
                        visible = true,
                        color = color,
                        highlight = PlaceholderHighlight.shimmer()
                    )
                    .fillMaxWidth()
            )
            Spacer(Modifier.padding(2.dp))
            Text(
                "Description",
                modifier = Modifier
                    .placeholder(
                        visible = true,
                        color = color,
                        highlight = PlaceholderHighlight.shimmer()
                    )
                    .fillMaxWidth()
            )
            Spacer(Modifier.padding(10.dp))
            Text(
                text = "Text",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .placeholder(
                        visible = true,
                        color = color,
                        highlight = PlaceholderHighlight.shimmer()
                    )
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun BreweryDetailShimmer() {
    val color = if (isSystemInDarkTheme()) lightBlack else Color.LightGray

    Card(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 10.dp
            )
        ) {
            Text(
                text = "Text",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier
                    .placeholder(
                        visible = true,
                        color = color,
                        highlight = PlaceholderHighlight.shimmer()
                    )
                    .fillMaxWidth()
            )
            Spacer(Modifier.padding(2.dp))
            Text(
                text = "Text",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier
                    .placeholder(
                        visible = true,
                        color = color,
                        highlight = PlaceholderHighlight.shimmer()
                    )
                    .fillMaxWidth()
            )
            Spacer(Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultDetailShimmerPreview() {
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
            SearchResultDetailShimmer()
        }

    }
}