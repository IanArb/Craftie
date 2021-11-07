package com.craftie.android

import CraftieTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.craftie.android.presentation.Screen
import com.craftie.android.presentation.beerByProvince.BeersByProvinceScreen
import com.craftie.android.presentation.beerDetail.BeerDetailScreen
import com.craftie.android.presentation.bottomNavigationItems
import com.craftie.android.presentation.discovery.DiscoveryScreen
import com.craftie.android.presentation.featuredBeer.FeaturedBeerScreen
import com.craftie.android.presentation.home.HomeScreen
import com.craftie.android.presentation.search.SearchResultDetailScreen
import com.craftie.android.presentation.search.SearchScreen
import com.craftie.android.presentation.viewAllBreweries.ViewAllBreweriesScreen
import com.craftie.android.presentation.viewAllRatings.ViewAllRatingsScreen
import com.craftie.android.presentation.viewAllTopRated.ViewAllBeersScreen
import com.craftie.android.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    CraftieTheme {
        val currentBackStackEntryAsState = navController.currentBackStackEntryAsState()
        val provinceScreen = Screen.BeersByProvinceScreen.title + "/{province}"
        val searchResultsScreen = Screen.SearchResultDetailScreen.title + "/{searchResultDetail}?beerName={beerName}"
        val viewAllRatingsScreen = Screen.ViewAllRatingsScreen.title + "/{beerDetail}"

        Scaffold(
            topBar = {
                currentBackStackEntryAsState.value?.let {
                    val route = it.destination.route
                    if (shouldShowForScreen(
                            navController = navController,
                            routes = listOf(
                                Screen.HomeScreen.title,
                                Screen.SearchScreen.title,
                                Screen.DiscoveryScreen.title
                            ))) {
                        TopAppBar(title = {
                            Text(text = route ?: "")
                        })
                    }

                    if (shouldShowForScreen(
                            navController = navController,
                            routes = listOf(
                                Screen.BreweriesViewAllScreen.title,
                                Screen.BeersViewAllScreen.title,
                                provinceScreen,
                                searchResultsScreen,
                                Screen.ViewAllRatingsScreen.title,
                                viewAllRatingsScreen
                            )
                        )
                    ) {
                        TopAppBar(
                            title = {
                                when (route) {
                                    provinceScreen -> {
                                        Text(text = it.arguments?.getString(Constants.PROVINCE_KEY) ?: "")
                                    }
                                    searchResultsScreen -> {
                                        Text(text = it.arguments?.getString(Constants.BEER_NAME_KEY) ?: "")
                                    }
                                    viewAllRatingsScreen -> {
                                        Text(text = "Ratings")
                                    }
                                    else -> {
                                        Text(text = route ?: "")
                                    }
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    navController.popBackStack()
                                }) {
                                    Icon(Icons.Default.ArrowBack, "Back")
                                }
                            }
                        )
                    }
                }
            },
            bottomBar = {
                if (shouldShowForScreen(
                        navController,
                        listOf(
                            Screen.HomeScreen.title,
                            Screen.SearchScreen.title,
                            Screen.DiscoveryScreen.title
                        )
                    )
                ) {
                    BottomNavigation {
                        val navBackStackEntry by currentBackStackEntryAsState
                        val currentRoute = navBackStackEntry?.destination?.route
                        bottomNavigationItems.map {
                            BottomNavigationItem(
                                selectedContentColor = Color.Blue,
                                unselectedContentColor = Color.Black,
                                label = {
                                    Text(text = it.route)
                                },
                                icon = {
                                    Icon(
                                        painterResource(id = it.icon),
                                        it.iconContentDescription
                                    )
                                },
                                selected = currentRoute == it.route,
                                onClick = {
                                    navController.navigate(it.route) {
                                        popUpTo(navController.graph.startDestinationRoute ?: "") {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        ) {
            NavHost(navController = navController, startDestination = Screen.HomeScreen.title) {
                composable(
                    Screen.HomeScreen.title,
                    arguments = listOf(
                        navArgument(Constants.BEER_NAME_KEY) {
                            type = NavType.StringType
                        }
                    )
                ) {
                    HomeScreen {
                        navController.navigate(Screen.SearchResultDetailScreen.title + "/${it.first}?beerName=${it.second}")
                    }
                }
                composable(
                    Screen.DiscoveryScreen.title,
                    arguments = listOf(
                        navArgument(Constants.PROVINCE_KEY) {
                            type = NavType.StringType
                        },
                        navArgument(Constants.BEER_ID_KEY) {
                            type = NavType.StringType
                        }
                    )
                ) {
                    DiscoveryScreen(
                        onFeaturedClick = {
                            navController.navigate(Screen.FeaturedBeerScreen.title)
                        },
                        onViewAllBreweriesClick = {
                            navController.navigate(Screen.BreweriesViewAllScreen.title)
                        },
                        onViewAllBeersClick = {
                            navController.navigate(Screen.BeersViewAllScreen.title)
                        },
                        onProvinceClick = {
                            navController.navigate(Screen.BeersByProvinceScreen.title + "/${it}")
                        },
                        onTopRatedBeerClick = {
                            navController.navigate(Screen.BeerDetailScreen.title + "/${it}")
                        },
                        onNewBeerClick = {
                            navController.navigate(Screen.BeerDetailScreen.title + "/${it}")
                        }
                    )
                }

                composable(
                    Screen.SearchScreen.title,
                    arguments = listOf(
                        navArgument(Constants.BEER_ID_KEY) {
                            type = NavType.StringType
                        },
                        navArgument(Constants.BEER_NAME_KEY) {
                            type = NavType.StringType
                            defaultValue = "Beer"
                        }
                    )
                ) {
                    SearchScreen(
                        onClick = {
                            navController.navigate(Screen.SearchResultDetailScreen.title + "/${it.first}?beerName=${it.second}")
                        },
                        onRecentSearchClick = {
                            navController.navigate(Screen.SearchResultDetailScreen.title + "/${it.first}?beerName=${it.second}")
                        }
                    )
                }

                composable(Screen.FeaturedBeerScreen.title) {
                    FeaturedBeerScreen {
                        navController.popBackStack()
                    }
                }

                composable(Screen.BreweriesViewAllScreen.title) {
                    ViewAllBreweriesScreen()
                }

                composable(Screen.BeersViewAllScreen.title) {
                    ViewAllBeersScreen()
                }

                composable(provinceScreen) {
                    BeersByProvinceScreen()
                }

                composable(Screen.BeerDetailScreen.title + "/{beerDetail}") {
                    BeerDetailScreen {
                        navController.popBackStack()
                    }
                }

                composable(
                    Screen.SearchResultDetailScreen.title + "/{searchResultDetail}?beerName={beerName}",
                    arguments = listOf(
                        navArgument(Constants.BEER_ID_KEY) {
                            type = NavType.StringType
                        }
                    )) {
                    SearchResultDetailScreen {
                        navController.navigate(Screen.ViewAllRatingsScreen.title + "/$it")
                    }
                }

                composable(Screen.ViewAllRatingsScreen.title + "/{beerDetail}") {
                    ViewAllRatingsScreen()
                }
            }
        }
    }
}

@Composable
fun shouldShowForScreen(navController: NavHostController, routes: List<String>): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return routes.contains(navBackStackEntry?.destination?.route ?: "")
}


@Preview
@Composable
fun DefaultPreview() {
    CraftieTheme {

    }
}
