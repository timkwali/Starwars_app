package com.timkwali.starwarsapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.compose.collectAsLazyPagingItems
import com.timkwali.starwarsapp.core.utils.ErrorType
import com.timkwali.starwarsapp.core.utils.UiState
import com.timkwali.starwarsapp.details.presentation.events.CharacterDetailsEvent
import com.timkwali.starwarsapp.details.presentation.screens.DetailsScreen
import com.timkwali.starwarsapp.details.presentation.viewmodel.CharacterDetailsViewModel
import com.timkwali.starwarsapp.search.domain.model.character.Character
import com.timkwali.starwarsapp.search.presentation.events.SearchEvent
import com.timkwali.starwarsapp.search.presentation.screens.SearchScreen
import com.timkwali.starwarsapp.search.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.emptyFlow

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
            val searchState = searchViewModel.characterState.value
            var searchValue by rememberSaveable{ mutableStateOf("") }
//            if(searchState is UiState.Loaded &&
//                (searchState.data != emptyFlow<PagingData<Character>>()) &&
//                searchState.data.collectAsLazyPagingItems().itemSnapshotList.isEmpty()
//            )  { searchViewModel.setCharacterErrorState(ErrorType.Api.EmptyListError) }

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

            val characterDetailsState = characterDetailsViewModel.characterDetails.value
            val speciesState = characterDetailsViewModel.speciesState.value
            val filmsState = characterDetailsViewModel.filmsState.value
            val homeWorldState = characterDetailsViewModel.homeWorldState.value
            val characterDetailsData = if(characterDetailsState is UiState.Loaded) characterDetailsState.data else null

            val isErrorState = characterDetailsState is UiState.Error || homeWorldState is UiState.Error ||
                    speciesState is UiState.Error || filmsState is UiState.Error
            val errorMessage: String = if(characterDetailsState is UiState.Error) characterDetailsState.error.asString()
                else if(homeWorldState is UiState.Error) homeWorldState.error.asString()
                else if(speciesState is UiState.Error) speciesState.error.asString()
                else if(filmsState is UiState.Error) filmsState.error.asString()
                else ""


            DetailsScreen(
                navigateBack = { navController.popBackStack() },
                characterId = characterId,
                characterDetailsState = characterDetailsState,
                speciesState = speciesState,
                filmsState = filmsState,
                homeWorldState = homeWorldState,
                isErrorState = isErrorState,
                errorMessage = errorMessage,
                getCharacterDetails = {
                    characterDetailsViewModel.onEvent(
                        CharacterDetailsEvent.GetCharacterDetails(characterId)
                    )
                },
                getSpecies = {
                    characterDetailsViewModel.onEvent(
                        CharacterDetailsEvent.GetSpecies(characterDetailsData?.speciesUrl ?: emptyList())
                    )
                },
                getHomeWorld = {
                    characterDetailsViewModel.onEvent(
                        CharacterDetailsEvent.GetHomeWorld(characterDetailsData?.homeWorldUrl ?: "")
                    )
                },
                getFilms = {
                    characterDetailsViewModel.onEvent(
                        CharacterDetailsEvent.GetFilms(characterDetailsData?.filmsUrl ?: emptyList())
                    )
                }
            )
        }
    }
}