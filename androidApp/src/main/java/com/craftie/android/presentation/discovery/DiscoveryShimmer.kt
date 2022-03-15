package com.craftie.android.presentation.discovery

import CraftieTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

@Composable
fun DiscoveryShimmer() {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            CirclePlaceholder("Breweries Nearby")
            Spacer(modifier = Modifier.padding(10.dp))
            Featured()
            Spacer(modifier = Modifier.padding(10.dp))
            CardShimmer(
                title = "Top Rated",
                showViewAll = true
            )
            Spacer(modifier = Modifier.padding(10.dp))
            CirclePlaceholder("By Province")
            Spacer(modifier = Modifier.padding(10.dp))
            CardShimmer(
                title = "New Beers"
            )
            Spacer(modifier = Modifier.padding(30.dp))
        }
    }
}

@Composable
fun CirclePlaceholder(title: String) {
    Header(title) {
        //noop
    }

    Spacer(modifier = Modifier.padding(10.dp))

    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(count = 10) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .placeholder(
                        visible = true,
                        color = Color.LightGray,
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
        }
    }
}

@Composable
fun Featured() {
    Text("Featured", fontWeight = FontWeight.Bold)

    Spacer(modifier = Modifier.padding(10.dp))

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clip(RectangleShape)
            .height(200.dp)
            .placeholder(
                visible = true,
                color = Color.LightGray,
                highlight = PlaceholderHighlight.shimmer()
            )
    )
}

@Composable
fun CardShimmer(title: String, showViewAll: Boolean = false) {
    if (showViewAll) {
        Header(title) {
            //noop
        }
    } else {
        Text(title, fontWeight = FontWeight.Bold)
    }

    Spacer(Modifier.padding(10.dp))
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(count = 10) {
            Card(
                modifier = Modifier
                    .size(120.dp, 150.dp)
                    .placeholder(
                        visible = true,
                        color = Color.LightGray,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                shape = RoundedCornerShape(8.dp)
            ) {

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DiscoveryShimmerPreview() {
    CraftieTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Discovery")
                    }
                )
            }
        ) {
            DiscoveryShimmer()
        }
    }
}