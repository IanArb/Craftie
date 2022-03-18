package com.craftie.android.presentation.viewAllRatings

import CraftieTheme
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.craftie.android.presentation.components.CircularProgressBar
import com.craftie.android.presentation.components.ratingBar.RatingBar
import com.craftie.android.presentation.components.NoResultsCard
import com.craftie.data.model.RatingResponse

@ExperimentalFoundationApi
@Composable
fun ViewAllRatingsScreen() {
    val viewModel = hiltViewModel<ViewAllRatingsViewModel>()

    viewModel.fetchRatings()

    val state = viewModel.uiState.collectAsState()

    when (val value = state.value) {
        is ViewAllRatingsUiState.Success -> {
            RatingsGrid(ratings = value.ratings)
        }

        is ViewAllRatingsUiState.Empty -> {
            NoResultsCard {

            }
        }

        is ViewAllRatingsUiState.Error -> {
            NoResultsCard {

            }
        }

        is ViewAllRatingsUiState.Loading -> {
            CircularProgressBar()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun RatingsGrid(ratings: List<RatingResponse>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2)
        ) {
            items(ratings) { rating ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Spacer(Modifier.padding(top = 8.dp))

                            RatingBar(
                                value = rating.rating.toFloat(),
                                isIndicator = true,
                                size = 16.dp,
                                onRatingChanged = {

                            })

                            Spacer(Modifier.padding(top = 8.dp))

                            Row {
                                Image(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Author"
                                )
                                if (rating.authorName.isNullOrEmpty()) {
                                    Text(
                                        fontWeight = FontWeight.Medium,
                                        text = "Anonymous",
                                        modifier = Modifier.padding(start = 2.dp)
                                    )
                                } else {
                                    Text(
                                        fontWeight = FontWeight.Light,
                                        text = rating.authorName ?: "Anonymous",
                                        modifier = Modifier.padding(start = 2.dp)
                                    )
                                }
                            }


                            Spacer(Modifier.padding(top = 8.dp))

                            rating.description?.let {
                                Text(it)
                            }

                            Spacer(Modifier.padding(6.dp))
                        }


                    }
                }

            }
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun PreviewRatings() {
    CraftieTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Ratings")
                    }
                )
            }
        ) {
            ViewAllRatingsScreen()
        }

    }
}