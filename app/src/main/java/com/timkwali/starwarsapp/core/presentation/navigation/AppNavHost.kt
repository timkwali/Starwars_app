package com.timkwali.starwarsapp.core.presentation.navigation

import android.util.Log
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
import com.timkwali.starwarsapp.core.utils.UiState
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
                searchState = searchViewModel.characterState.value,
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

            val characterDetails = characterDetailsViewModel.characterDetails.value
            val speciesState = characterDetailsViewModel.speciesState.value
            val filmsState = characterDetailsViewModel.filmsState.value
            val homeWorldState = characterDetailsViewModel.homeWorldState.value

            val cd = if(characterDetails is UiState.Loaded) characterDetails.data else null
            val sp = if(speciesState is UiState.Loaded) speciesState.data else null
            val fm = if(filmsState is UiState.Loaded) filmsState.data else null
            val hw = if(homeWorldState is UiState.Loaded) homeWorldState.data else null

            val isCharacterLoading = characterDetails is UiState.Loading
            val isSpeciesLoading = speciesState is UiState.Loading
            val isFilmsLoading = filmsState is UiState.Loading
            val isHomeWorldLoading = homeWorldState is UiState.Loading

            val isErrorState: Boolean = characterDetails is UiState.Error ||
                    homeWorldState is UiState.Error || speciesState is UiState.Error ||
                    filmsState is UiState.Error

            val errorMessage: String? = if(characterDetails is UiState.Error) characterDetails.error.asString()
                else if(homeWorldState is UiState.Error) homeWorldState.error.asString()
                else if(speciesState is UiState.Error) speciesState.error.asString()
                else if(filmsState is UiState.Error) filmsState.error.asString()
                else ""


            DetailsScreen(
                navigateBack = { navController.popBackStack() },
                characterId = characterId,
                characterDetails = cd,
                speciesState = sp,
                filmsState = fm,
                homeWorldState = hw,

                isCharacterLoading = isCharacterLoading,
                isSpeciesLoading = isSpeciesLoading,
                isFilmsLoading = isFilmsLoading,
                isErrorState = isErrorState,
                errorMessage = errorMessage,

                getCharacterDetails = {
                    characterDetailsViewModel.onEvent(
                        CharacterDetailsEvent.GetCharacterDetails(characterId)
                    )
                },
                getSpecies = {
                    characterDetailsViewModel.onEvent(
                        CharacterDetailsEvent.GetSpecies(cd?.speciesUrl ?: emptyList())
                    )
                },
                getHomeWorld = {
                    characterDetailsViewModel.onEvent(
                        CharacterDetailsEvent.GetHomeWorld(cd?.homeWorldUrl ?: "")
                    )
                },
                getFilms = {
                    characterDetailsViewModel.onEvent(
                        CharacterDetailsEvent.GetFilms(cd?.filmsUrl ?: emptyList())
                    )
                }
            )
        }
    }
}