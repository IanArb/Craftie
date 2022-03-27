package com.craftie.android.presentation.search

import CraftieTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.craftie.android.presentation.components.ratingBar.RatingBar
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import lightBlack
import lightGray

@Composable
fun SearchResultsShimmer() {
    LazyColumn(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 80.dp
        )
    ) {
        items(count = 5) {
            ResultsCardShimmer()
        }
    }
}

@Composable
fun ResultsCardShimmer() {
    val color = if (isSystemInDarkTheme()) lightBlack else Color.LightGray

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
    ) {
        Row {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .width(100.dp)
                    .height(150.dp)
                    .placeholder(
                        visible = true,
                        color = color,
                        highlight = PlaceholderHighlight.shimmer()
                    )
                    .fillMaxHeight(),
            )

            Column(
                modifier = Modifier.padding(
                    top = 26.dp
                )
            ) {
                Text(
                    text = "Text content",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .placeholder(
                            visible = true,
                            color = color,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                )
                Spacer(Modifier.padding(1.dp))
                Text(
                    text = "Text Content",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .placeholder(
                            visible = true,
                            color = color,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                )
                Spacer(Modifier.padding(1.dp))
                Text(
                    text = "Text Content",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .placeholder(
                            visible = true,
                            color = color,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                )
                Spacer(Modifier.padding(8.dp))
                val rating: Float by rememberSaveable { mutableStateOf(4.2f) }
                RatingBar(
                    value = rating,
                    isIndicator = true,
                    modifier = Modifier
                        .placeholder(
                            visible = true,
                            color = color,
                            highlight = PlaceholderHighlight.shimmer()
                        ),
                    onRatingChanged = {

                })
                Spacer(Modifier.padding(4.dp))
                Text(
                    text = "Based on 300 reviews",
                    fontSize = 10.sp,
                    modifier = Modifier
                        .placeholder(
                            visible = true,
                            color = color,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultsCardShimmerPreview() {
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
            SearchResultsShimmer()
        }

    }
}