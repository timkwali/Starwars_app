package com.timkwali.starwarsapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timkwali.starwarsapp.details.presentation.screens.DetailsScreen
import com.timkwali.starwarsapp.search.presentation.screens.SearchScreen

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route
    ) {
        composable(route = Screen.Search.route) {
            SearchScreen(navController = navController)
        }

        composable(route = Screen.Details.route) {
            DetailsScreen(navController = navController)
        }
    }
}