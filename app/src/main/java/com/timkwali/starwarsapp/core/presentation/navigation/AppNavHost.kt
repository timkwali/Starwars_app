package com.timkwali.starwarsapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timkwali.starwarsapp.details.presentation.events.CharacterDetailsEvent
import com.timkwali.starwarsapp.details.presentation.screens.DetailsScreen
import com.timkwali.starwarsapp.details.presentation.viewmodel.CharacterDetailsViewModel
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
            var searchValue by rememberSaveable{ mutableStateOf("") }

            SearchScreen(
                searchState = searchViewModel.characterState,
                navigateToDetailsScreen = { characterId ->
                    navController.navigate("${Screen.Details.route}/$characterId")
                },
                searchCharacters = { searchQuery->
                    searchViewModel.onEvent(SearchEvent.SearchCharacters(searchQuery))
                },
                searchValue = searchValue,
                onSearchChange = { newSearchValue ->  searchValue = newSearchValue}
            )
        }

        composable(
            route = "${Screen.Details.route}/{character_id}",
        ) {
            val characterId = it.arguments?.getString("character_id") ?: ""
            val characterDetailsViewModel = hiltViewModel<CharacterDetailsViewModel>()
            val characterDetails = characterDetailsViewModel.characterDetails.collectAsState()

            DetailsScreen(
                navigateBack = { navController.popBackStack() },
                characterId = characterId,
                characterDetails = characterDetails.value,
                getCharacterDetails = {
                    characterDetailsViewModel.onEvent(
                        CharacterDetailsEvent.GetCharacterDetails(characterId)
                    )
                },
                getSpecies = {
                    characterDetailsViewModel.onEvent(
                        CharacterDetailsEvent.GetSpecies(characterDetails.value?.speciesUrl ?: emptyList())
                    )
                },
                getHomeWorld = {
                    characterDetailsViewModel.onEvent(
                        CharacterDetailsEvent.GetHomeWorld(characterDetails.value?.homeWorldUrl ?: "")
                    )
                },
                getFilms = {
                    characterDetailsViewModel.onEvent(
                        CharacterDetailsEvent.GetFilms(characterDetails.value?.filmsUrl ?: emptyList())
                    )
                }
            )
        }
    }
}