package com.craftie.android.presentation.featuredBeer

import CraftieTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.craftie.android.R
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

@Composable
fun FeaturedBeerShimmerScaffold(popUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Featured",
                        modifier = Modifier
                            .placeholder(
                                visible = true,
                                color = Color.LightGray,
                                highlight = PlaceholderHighlight.shimmer()
                            ),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        popUp()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_24),
                            contentDescription = "Up"
                        )
                    }
                }
            )
        }
    ) {
        FeaturedBeerShimmer()
    }
}

@Composable
fun FeaturedBeerShimmer() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .placeholder(
                    visible = true,
                    color = Color.LightGray,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .placeholder(
                    visible = true,
                    color = Color.LightGray,
                    highlight = PlaceholderHighlight.shimmer()
                ),
            text = "Text to load before content",
            fontSize = 16.sp,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .placeholder(
                    visible = true,
                    color = Color.LightGray,
                    highlight = PlaceholderHighlight.shimmer()
                ),
            text = "Text to load before content",
            fontSize = 16.sp,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .placeholder(
                    visible = true,
                    color = Color.LightGray,
                    highlight = PlaceholderHighlight.shimmer()
                ),
            text = "Text to load before content",
            fontSize = 16.sp,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewFeaturedShimmer() {
    CraftieTheme {
        FeaturedBeerShimmerScaffold {

        }
    }
}