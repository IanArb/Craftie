package com.craftie.android.presentation

import androidx.annotation.DrawableRes
import com.craftie.android.R

sealed class Screen(val title: String) {
    object HomeScreen : Screen("Home")
    object DiscoveryScreen : Screen("Discovery")
    object SearchScreen : Screen("Search")
    object FeaturedBeerScreen : Screen("Featured Beer")
    object BreweriesViewAllScreen : Screen("Breweries")
    object BeersViewAllScreen : Screen("Beers")
}

data class BottomNavigationitem(
    val route: String,
    @DrawableRes val icon: Int,
    val iconContentDescription: String
)

val bottomNavigationItems = listOf(
    BottomNavigationitem(
        Screen.HomeScreen.title,
        R.drawable.ic_home_black_24dp,
        "Home"
    ),
    BottomNavigationitem(
        Screen.DiscoveryScreen.title,
        R.drawable.ic_map_black_24dp,
        "Discover"
    ),
    BottomNavigationitem(
        Screen.SearchScreen.title,
        R.drawable.ic_search_black_24dp,
        "Search"
    )
)