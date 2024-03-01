package com.timkwali.starwarsapp.details.presentation.viewmodel

import android.util.Log
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
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
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

    private var _speciesState : MutableState<UiState<List<Species>?>> = mutableStateOf(UiState.Loaded(null))
    val speciesState: State<UiState<List<Species>?>> get() = _speciesState
    private val speciesList: MutableList<Species> = mutableListOf()

    private var _filmsState : MutableState<UiState<List<Film>?>> = mutableStateOf(UiState.Loaded(null))
    val filmsState: State<UiState<List<Film>?>> get() = _filmsState
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
        try {
            _characterDetails.value = UiState.Loading()
            when(val resource = getCharacterDetails.invoke(characterId)) {
                is Resource.Success -> {
                    resource.data.collect {
                        _characterDetails.value = UiState.Loaded(it)
                    }}
                is Resource.Error -> {
                    _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter
                        .convert(ErrorType.Api.ServiceUnavailable))
                }
                else -> _characterDetails.value = UiState.Loading()
            }
        } catch (e: IOException) {
            _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter
                .convert(ErrorType.Api.Network))
        }catch (e: Exception) {
            _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter
                .convert(ErrorType.Unknown))
        }
    }

    private suspend fun getSpecies(species: List<String>) {
        species.forEach {
            try {
                if(it.isNotEmpty()) {
                    _speciesState.value = UiState.Loading()
                        val endpoint = it.substring(BASE_URL.length)
                        when(val resource = getSpecies.invoke(endpoint)) {
                            is Resource.Success -> resource.data.collect {
                                speciesList.add(it)
                                _speciesState.value = UiState.Loaded(speciesList)
                            }
                            is Resource.Error -> _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter
                                    .convert(ErrorType.Api.ServiceUnavailable))
                            else -> _characterDetails.value = UiState.Loading()
                        }
                }
            } catch (e: IOException) {
                _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter
                    .convert(ErrorType.Api.Network))
            }catch (e: Exception) {
                _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter
                    .convert(ErrorType.Unknown))
            }
        }
    }

    private suspend fun getHomeWorld(homeWorldUrl: String) {
        try {
            if(homeWorldUrl.isNotEmpty()) {
                val endpoint = homeWorldUrl.substring(BASE_URL.length)
                when(val resource = getHomeWorld.invoke(endpoint)) {
                    is Resource.Success -> resource.data.collect {
                        _homeWorldState.value = UiState.Loaded(it)
                    }
                    is Resource.Error -> _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter
                        .convert(ErrorType.Api.ServiceUnavailable))
                    else -> _characterDetails.value = UiState.Loading()
                }
            }
        } catch (e: IOException) {
            _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter
                .convert(ErrorType.Api.Network))
        }catch (e: Exception) {
            _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter
                .convert(ErrorType.Unknown))
        }

    }

    private suspend fun getFilms(filmsUrl: List<String>) {
        filmsUrl.forEach {
            try {
                if(it.isNotEmpty()) {
                    _filmsState.value = UiState.Loading()
                    val endpoint = it.substring(BASE_URL.length)
                    when(val resource = getFilm.invoke(endpoint)) {
                        is Resource.Success -> resource.data.collect {
                            filmList.add(it)
                            _filmsState.value = UiState.Loaded(filmList)
                        }
                        is Resource.Error -> {
                            _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter
                                .convert(ErrorType.Api.ServiceUnavailable))
                        }
                        else -> _characterDetails.value = UiState.Loading()
                    }
                }
            } catch (e: IOException) {
                _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter
                    .convert(ErrorType.Api.Network))
            }catch (e: Exception) {
                _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter
                    .convert(ErrorType.Unknown))
            }
        }
    }
}