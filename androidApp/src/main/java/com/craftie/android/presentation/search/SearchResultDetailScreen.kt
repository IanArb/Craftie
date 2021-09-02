package com.craftie.android.presentation.search

import CraftieTheme
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.craftie.android.presentation.beerDetail.BeerDetailUiState
import com.craftie.android.presentation.beerDetail.BeerDetailViewModel
import com.craftie.android.presentation.components.ButtonState
import com.craftie.android.presentation.components.gradientImageView
import com.craftie.android.presentation.components.ratingBar.RatingBar
import com.craftie.android.presentation.components.rememberMapViewWithLifecycle
import com.craftie.android.presentation.discovery.NoResultsCard
import com.craftie.android.util.MockData
import com.craftie.data.model.Beer
import com.craftie.data.model.BreweryInfo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import darkGray
import gray
import kotlinx.coroutines.launch
import lightGray
import orange
import yellow
import kotlin.math.roundToInt

@Composable
fun SearchResultDetailScreen() {
    val viewModel = hiltViewModel<BeerDetailViewModel>()

    viewModel.init()

    val uiState = viewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is BeerDetailUiState.Success -> {
            val beer = state.beer
            val breweryInfo = beer.breweryInfo
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier.padding(bottom = 24.dp)
                    ) {
                        BeerDetail(beer,
                            onReviewClick = {

                            },
                            onFavouriteClick = {
                                viewModel.saveBeer(beer)
                            }
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        BeerDescription(beer)
                        BreweryDetail(breweryInfo)
                        BreweryLocation(breweryInfo)
                    }
                }
            }
        }

        is BeerDetailUiState.Error -> {
            NoResultsCard {

            }
        }

        is BeerDetailUiState.Loading -> {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun BeerDetail(
    beer: Beer,
    onReviewClick: () -> Unit,
    onFavouriteClick: () -> Unit
) {
    Box {
        Image(
            painter = rememberImagePainter(
                data = beer.breweryInfo.brandImageUrl,
                builder = {
                    crossfade(true)
                }
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .gradientImageView()
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
                                .background(yellow)
                                .constrainAs(icon) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                }
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        start = 10.dp,
                                        end = 10.dp,
                                        top = 6.dp,
                                        bottom = 6.dp
                                    ),
                                text = beer.type,
                                color = Color.Black,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        }

                        var buttonState: ButtonState by remember { mutableStateOf(ButtonState.IDLE) }

                        Box(
                            modifier = Modifier
                                .padding(top = 6.dp, end = 72.dp)
                                .clip(shape = CircleShape)
                                .background(lightGray)
                                .constrainAs(favourite) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.end)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            val asset = if (buttonState == ButtonState.PRESSED) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            }



                            val tintColor: Color by animateColorAsState(
                                if (buttonState == ButtonState.PRESSED)
                                    Color.Red
                                else
                                    Color.Black
                            )

                            Image(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .clickable {
                                        buttonState =
                                            if (buttonState == ButtonState.IDLE) {
                                                onFavouriteClick()
                                                ButtonState.PRESSED
                                            } else {
                                                ButtonState.IDLE
                                            }
                                    },
                                imageVector = asset,
                                contentDescription = "Favourite",
                                colorFilter = ColorFilter.tint(tintColor)
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
                        color = gray,
                        text = beer.breweryInfo.name
                    )
                    Spacer(Modifier.padding(1.dp))
                    Text(
                        text = beer.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Spacer(Modifier.padding(2.dp))
                    RatingBar(value = 4.0f, isIndicator = true, onRatingChanged = {

                    })
                    Spacer(Modifier.padding(2.dp))
                    Text(
                        text = "Based on 300 reviews",
                        fontSize = 12.sp,
                        color = darkGray
                    )
                    Spacer(Modifier.padding(10.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 12.dp,
                                end = 12.dp
                            ),
                        colors = ButtonDefaults.buttonColors(backgroundColor = orange),
                        onClick = {
                            onReviewClick()
                        }
                    ) {
                        Text(
                            text = "REVIEW",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(Modifier.padding(10.dp))
                }

            }
        }

        Image(
            painter = rememberImagePainter(
                data = beer.imageUrl,
                builder = {
                    crossfade(true)
                }
            ),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
                .align(alignment = Alignment.BottomEnd)
                .offset(y = 16.dp)
        )
    }

}

@Composable
fun BeerDescription(beer: Beer) {
    Card(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 200.dp
        )
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
                fontSize = 16.sp
            )
            Spacer(Modifier.padding(2.dp))
            Text(
                text = beer.description,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )
            Spacer(Modifier.padding(10.dp))
            BeerStrength(beer)
        }
    }
}

@Composable
private fun BeerStrength(beer: Beer) {
    ConstraintLayout(
        modifier = Modifier
            .padding(bottom = 30.dp)
    ) {
        val (abv, ibu) = createRefs()

        val ibuText = beer.ibu?.value?.roundToInt() ?: 0
        val abvText = "ABV: ${beer.abv.value} ${beer.abv.unit}"

        Text(
            text = abvText,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(abv) {
                    if (ibuText > 0) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, margin = 50.dp)
                    } else {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                }
        )

        if (ibuText > 0) {
            Text(
                text = "IBU: $ibuText",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .constrainAs(ibu) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.end, margin = 85.dp)
                    }
            )
        }
    }
}

@Composable
fun BreweryDetail(breweryInfo: BreweryInfo) {
    Card(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp
        )
    ) {
        DetailInfo("About the Brewery", breweryInfo.description)
    }
}

@Composable
private fun DetailInfo(title: String, description: String) {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 10.dp
        )
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Spacer(Modifier.padding(2.dp))
        Text(
            text = description,
            fontSize = 12.sp,
            letterSpacing = 1.sp
        )
        Spacer(Modifier.padding(8.dp))
    }
}

@Composable
fun BreweryLocation(breweryInfo: BreweryInfo) {
    Card(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
            .fillMaxWidth()
    ) {
        Column {
            DetailInfo("Location", breweryInfo.location.address)
            CityMapView(
                latitude = breweryInfo.location.latLng.latitude,
                longitude = breweryInfo.location.latLng.longitude
            )
        }
    }
}

@Composable
private fun CityMapView(latitude: Double, longitude: Double) {
    val mapView = rememberMapViewWithLifecycle()
    MapViewContainer(mapView, latitude, longitude)
}

@Composable
private fun MapViewContainer(
    map: MapView,
    latitude: Double,
    longitude: Double
) {
    val cameraPosition = remember(latitude, longitude) {
        LatLng(latitude, longitude)
    }

    LaunchedEffect(map) {
        val googleMap = map.awaitMap()
        googleMap.addMarker { position(cameraPosition) }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
    }

    val coroutineScope = rememberCoroutineScope()
    AndroidView({ map },
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)) { mapView ->
        coroutineScope.launch {
            val googleMap = mapView.awaitMap()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, 16.0f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultsDetailScreenPreview() {
    CraftieTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Five Lamps Ale")
                    }
                )
            }
        ) {
            val beer = MockData.beers().first()
            LazyColumn {
                item {
                    Column {
                        BeerDetail(
                            beer,
                            onReviewClick = {},
                            onFavouriteClick = {}
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        BeerDescription(beer)
                        BreweryDetail(beer.breweryInfo)
                        BreweryLocation(beer.breweryInfo)
                    }
                }

            }
        }
    }
}