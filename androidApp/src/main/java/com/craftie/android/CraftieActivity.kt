package com.craftie.android

import CraftieTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.craftie.android.presentation.Screen
import com.craftie.android.presentation.bottomNavigationItems
import com.craftie.android.presentation.discovery.screen.DiscoveryScreen
import com.craftie.android.presentation.featuredBeer.screen.FeaturedBeerScreen
import com.craftie.android.presentation.home.HomeScreen
import com.craftie.android.presentation.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    CraftieTheme {
        val currentBackStackEntryAsState = navController.currentBackStackEntryAsState()
        Scaffold(
            topBar = {
                currentBackStackEntryAsState.value?.let {
                    if (it.destination.route != Screen.FeaturedBeerScreen.title) {
                        TopAppBar(title = {
                            Text(text = it.destination.route ?: "")
                        })
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
                composable(Screen.HomeScreen.title) {
                    HomeScreen()
                }
                composable(Screen.DiscoveryScreen.title) {
                    DiscoveryScreen {
                        navController.navigate(Screen.FeaturedBeerScreen.title)
                    }
                }
                composable(Screen.SearchScreen.title) {
                    SearchScreen()
                }
                composable(Screen.FeaturedBeerScreen.title) {
                    FeaturedBeerScreen {
                        navController.popBackStack()
                    }
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
