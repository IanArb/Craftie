package com.craftie.android.presentation.featuredBeer

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.craftie.android.R
import com.craftie.android.presentation.components.*
import com.craftie.android.presentation.discovery.screen.NoResultsCard
import com.craftie.data.model.Beer
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun FeaturedBeerScreen(popUp: () -> Unit) {
    val viewModel = hiltViewModel<FeaturedBeerViewModel>()

    viewModel.init()

    val uiState = viewModel.uiState.collectAsState()

    when (uiState.value) {
        is FeaturedBeerUiState.Success -> {
            val value = uiState.value as FeaturedBeerUiState.Success
            FeaturedBeer(value.beer) {
                popUp()
            }
        }
        is FeaturedBeerUiState.Error -> {
            ErrorView()
        }
        is FeaturedBeerUiState.Loading -> {
            CircularProgressBar()
        }
    }
}

@Composable
fun FeaturedBeer(featuredBeer: Beer, popUp: () -> Unit) {
    val state = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {

            val isCollapsedState = state.toolbarState.progress == 0.0f
            if (!isCollapsedState) {
                Image(
                    painter = rememberCoilPainter(
                        featuredBeer.breweryInfo.brandImageUrl,
                        fadeIn = true
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .pin()
                        .gradientImageView()
                )
            }

            val arrowDrawable = if (!isCollapsedState) painterResource(id = R.drawable.out_arrow_back_white_24) else painterResource(id = R.drawable.outline_arrow_back_24)

            Text(
                text = featuredBeer.name.uppercase(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .road(Alignment.CenterStart, Alignment.BottomStart)
                    .padding(60.dp, 16.dp, 16.dp, 16.dp)
                    .width(175.dp),
                color = if (isCollapsedState) Color.Black else Color.White,
                fontSize = 16.sp,
                maxLines = 3
            )

            Image(
                modifier = Modifier
                    .pin()
                    .padding(16.dp)
                    .clickable {
                        popUp()
                    },
                painter = arrowDrawable,
                contentDescription = null
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = featuredBeer.description,
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
fun ErrorView() {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "")
        })
    }) {
        NoResultsCard {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeaturedBeer() {
    FeaturedBeerScreen {

    }
}

