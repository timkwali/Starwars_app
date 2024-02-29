package com.timkwali.starwarsapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timkwali.starwarsapp.details.presentation.screens.DetailsScreen
import com.timkwali.starwarsapp.search.presentation.screens.SearchScreen
import com.timkwali.starwarsapp.search.presentation.viewmodel.SearchViewModel

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route
    ) {
        composable(route = Screen.Search.route) {
            val searchViewModel = hiltViewModel<SearchViewModel>()

            SearchScreen(
                searchState = searchViewModel.ss,
                navigateToDetailsScreen = { navController.navigate(Screen.Details.route) },
                searchCharacters = { searchQuery->
                    searchViewModel.searchCharacters(searchQuery)
                }
            )
        }

        composable(route = Screen.Details.route) {
            DetailsScreen(navigateBack = { navController.popBackStack() })
        }
    }
}