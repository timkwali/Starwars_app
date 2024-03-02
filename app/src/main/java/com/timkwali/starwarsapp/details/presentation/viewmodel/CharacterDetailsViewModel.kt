package com.timkwali.starwarsapp.details.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkwali.starwarsapp.core.utils.Constants.BASE_URL
import com.timkwali.starwarsapp.core.utils.ErrorType
import com.timkwali.starwarsapp.core.utils.ErrorTypeToErrorTextConverter
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.UiState
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetails
import com.timkwali.starwarsapp.details.domain.model.film.Film
import com.timkwali.starwarsapp.details.domain.model.homeworld.HomeWorld
import com.timkwali.starwarsapp.details.domain.model.species.Species
import com.timkwali.starwarsapp.details.domain.usecase.GetCharacterDetails
import com.timkwali.starwarsapp.details.domain.usecase.GetFilm
import com.timkwali.starwarsapp.details.domain.usecase.GetHomeWorld
import com.timkwali.starwarsapp.details.domain.usecase.GetSpecies
import com.timkwali.starwarsapp.details.presentation.events.CharacterDetailsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetails: GetCharacterDetails,
    private val getSpecies: GetSpecies,
    private val getHomeWorld: GetHomeWorld,
    private val getFilm: GetFilm,
    private val errorTypeToErrorTextConverter: ErrorTypeToErrorTextConverter
): ViewModel() {

    private var _characterDetails: MutableState<UiState<CharacterDetails?>> = mutableStateOf(UiState.Loaded(null))
    val characterDetails: State<UiState<CharacterDetails?>> get() = _characterDetails

    private var _speciesState : MutableState<UiState<List<Species>>> = mutableStateOf(UiState.Loaded(emptyList()))
    val speciesState: State<UiState<List<Species>>> get() = _speciesState
    private val speciesList: MutableList<Species> = mutableListOf()

    private var _filmsState : MutableState<UiState<List<Film>>> = mutableStateOf(UiState.Loaded(emptyList()))
    val filmsState: State<UiState<List<Film>>> get() = _filmsState
    private val filmList: MutableList<Film> = mutableListOf()

    private var _homeWorldState : MutableState<UiState<HomeWorld?>> = mutableStateOf(UiState.Loaded(null))
    val homeWorldState: State<UiState<HomeWorld?>> get() = _homeWorldState

    fun onEvent(event: CharacterDetailsEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when(event) {
                is CharacterDetailsEvent.GetCharacterDetails -> getCharacterDetails(event.characterId)
                is CharacterDetailsEvent.GetSpecies -> getSpecies(event.species)
                is CharacterDetailsEvent.GetHomeWorld -> getHomeWorld(event.homeWorldUrl)
                is CharacterDetailsEvent.GetFilms -> getFilms(event.filmsUrl)
            }
        }
    }

    private suspend fun getCharacterDetails(characterId: String) {
        _characterDetails.value = UiState.Loading()
        getCharacterDetails.invoke(characterId).collectLatest {
            when(it) {
                is Resource.Success -> _characterDetails.value = UiState.Loaded(it.data)
                is Resource.Error -> _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter.convert(it.error))
            }
        }
    }

    private suspend fun getSpecies(species: List<String>) {
        species.forEach { specie ->
            if(specie.isNotEmpty()) {
                _speciesState.value = UiState.Loading()
                val endpoint = specie.substring(BASE_URL.length)
                getSpecies.invoke(endpoint).collectLatest { resource ->
                    when(resource) {
                        is Resource.Success -> {
                            speciesList.add(resource.data)
                            _speciesState.value = UiState.Loaded(speciesList)
                        }
                        is Resource.Error -> _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter.convert(resource.error))
                    }
                }
            }
        }
    }

    private suspend fun getHomeWorld(homeWorldUrl: String) {
        if(homeWorldUrl.isNotEmpty()) {
            val endpoint = homeWorldUrl.substring(BASE_URL.length)
            getHomeWorld.invoke(endpoint).collectLatest { resource ->
                when(resource) {
                    is Resource.Success -> _homeWorldState.value = UiState.Loaded(resource.data)
                    is Resource.Error -> _homeWorldState.value = UiState.Error(errorTypeToErrorTextConverter.convert(resource.error))
                }
            }
        }
    }

    private suspend fun getFilms(filmsUrl: List<String>) {
        filmsUrl.forEach { filmUrl ->
            if(filmUrl.isNotEmpty()) {
                _filmsState.value = UiState.Loading()
                val endpoint = filmUrl.substring(BASE_URL.length)
                getFilm.invoke(endpoint).collectLatest { resource ->
                    when(resource) {
                        is Resource.Success -> {
                            filmList.add(resource.data)
                            _filmsState.value = UiState.Loaded(filmList)
                        }
                        is Resource.Error -> _filmsState.value = UiState.Error(errorTypeToErrorTextConverter.convert(resource.error))
                    }
                }
            }
        }
    }
}