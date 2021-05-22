package com.craftie.android.ui.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val title: String) {
    object HomeScreen : Screen("Home")
    object DiscoveryScreen : Screen("Discovery")
    object SearchScreen : Screen("Search")
}

data class BottomNavigationitem(
    val route: String,
    val icon: ImageVector,
    val iconContentDescription: String
)

val bottomNavigationItems = listOf(
    BottomNavigationitem(
        Screen.HomeScreen.title,
        Icons.Default.Home,
        "Home"
    ),
    BottomNavigationitem(
        Screen.DiscoveryScreen.title,
        Icons.Filled.Menu,
        "Discover"
    ),
    BottomNavigationitem(
        Screen.SearchScreen.title,
        Icons.Default.Search,
        "Search"
    )
)