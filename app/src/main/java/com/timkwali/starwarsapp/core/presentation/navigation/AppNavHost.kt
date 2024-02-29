package com.timkwali.starwarsapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timkwali.starwarsapp.details.presentation.screens.DetailsScreen
import com.timkwali.starwarsapp.search.presentation.events.SearchEvent
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
                searchState = searchViewModel.searchState,
                navigateToDetailsScreen = { characterId ->
                    navController.navigate("${Screen.Details.route}/$characterId")
                },
                searchCharacters = { searchQuery->
                    searchViewModel.onEvent(SearchEvent.SearchCharacters(searchQuery))
                }
            )
        }

        composable(
            route = "${Screen.Details.route}/{character_id}",
        ) {
            val characterId = it.arguments?.getString("character_id") ?: ""
            DetailsScreen(
                navigateBack = { navController.popBackStack() },
                characterUrl = characterId
            )
        }
    }
}