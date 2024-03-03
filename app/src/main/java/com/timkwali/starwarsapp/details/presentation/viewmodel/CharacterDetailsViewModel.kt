package com.timkwali.starwarsapp.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkwali.starwarsapp.core.utils.Constants.BASE_URL
import com.timkwali.starwarsapp.core.utils.ErrorTypeToErrorTextConverter
import com.timkwali.starwarsapp.core.utils.InternetConnection
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.UiState
import com.timkwali.starwarsapp.core.utils.toErrorType
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetails: GetCharacterDetails,
    private val getSpecies: GetSpecies,
    private val getHomeWorld: GetHomeWorld,
    private val getFilm: GetFilm,
    private val errorTypeToErrorTextConverter: ErrorTypeToErrorTextConverter,
    private val internetConnection: InternetConnection
): ViewModel() {

    private var _characterDetails: MutableStateFlow<UiState<CharacterDetails?>> = MutableStateFlow(UiState.Loaded(null))
    val characterDetails: StateFlow<UiState<CharacterDetails?>> get() = _characterDetails.asStateFlow()

    private var _speciesState : MutableStateFlow<UiState<List<Species>>> = MutableStateFlow(UiState.Loaded(emptyList()))
    val speciesState: StateFlow<UiState<List<Species>>> get() = _speciesState.asStateFlow()
    private val speciesList: MutableList<Species> = mutableListOf()

    private var _filmsState : MutableStateFlow<UiState<List<Film>>> = MutableStateFlow(UiState.Loaded(emptyList()))
    val filmsState: StateFlow<UiState<List<Film>>> get() = _filmsState.asStateFlow()
    private val filmList: MutableList<Film> = mutableListOf()

    private var _homeWorldState : MutableStateFlow<UiState<HomeWorld?>> = MutableStateFlow(UiState.Loaded(null))
    val homeWorldState: StateFlow<UiState<HomeWorld?>> get() = _homeWorldState.asStateFlow()

    fun onEvent(event: CharacterDetailsEvent) {
        when(event) {
            is CharacterDetailsEvent.GetCharacterDetails -> getCharacterDetails(event.characterId)
            is CharacterDetailsEvent.GetSpecies -> getSpecies(event.species)
            is CharacterDetailsEvent.GetHomeWorld -> getHomeWorld(event.homeWorldUrl)
            is CharacterDetailsEvent.GetFilms -> getFilms(event.filmsUrl)
        }
    }

    private fun getCharacterDetails(characterId: String) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(!internetConnection.isInternetAvailable()) {
                throw IOException()
            } else {
                _characterDetails.value = UiState.Loading()
                getCharacterDetails.invoke(characterId).collectLatest {
                    when(it) {
                        is Resource.Success -> _characterDetails.value = UiState.Loaded(it.data)
                        is Resource.Error -> _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter.convert(it.error))
                    }
                }
            }
        } catch (e: IOException) {
            _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter.convert(e.toErrorType()))
        } catch (e: Exception) {
            _characterDetails.value = UiState.Error(errorTypeToErrorTextConverter.convert(e.toErrorType()))
        }

    }

    private fun getSpecies(species: List<String>)  = viewModelScope.launch(Dispatchers.IO){
        species.forEach { specie ->
            try {
                if(specie.isNotEmpty()) {
                    if(!internetConnection.isInternetAvailable()) {
                        throw IOException()
                    } else {
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
            } catch (e: IOException) {
                _speciesState.value = UiState.Error(errorTypeToErrorTextConverter.convert(e.toErrorType()))
            } catch (e: Exception) {
                _speciesState.value = UiState.Error(errorTypeToErrorTextConverter.convert(e.toErrorType()))
            }
        }
    }

    private fun getHomeWorld(homeWorldUrl: String)  = viewModelScope.launch(Dispatchers.IO){
        try {
            if(homeWorldUrl.isNotEmpty()) {
                if(!internetConnection.isInternetAvailable()) {
                    throw IOException()
                } else {
                    val endpoint = homeWorldUrl.substring(BASE_URL.length)
                    getHomeWorld.invoke(endpoint).collectLatest { resource ->
                        when(resource) {
                            is Resource.Success -> _homeWorldState.value = UiState.Loaded(resource.data)
                            is Resource.Error -> _homeWorldState.value = UiState.Error(errorTypeToErrorTextConverter.convert(resource.error))
                        }
                    }
                }
            }
        } catch (e: IOException) {
            _homeWorldState.value = UiState.Error(errorTypeToErrorTextConverter.convert(e.toErrorType()))
        } catch (e: Exception) {
            _homeWorldState.value = UiState.Error(errorTypeToErrorTextConverter.convert(e.toErrorType()))
        }
    }

    private fun getFilms(filmsUrl: List<String>)  = viewModelScope.launch(Dispatchers.IO){
        filmsUrl.forEach { filmUrl ->
            try {
                if(filmUrl.isNotEmpty()) {
                    if(!internetConnection.isInternetAvailable()) {
                        throw IOException()
                    } else {
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
            } catch (e: IOException) {
                _filmsState.value = UiState.Error(errorTypeToErrorTextConverter.convert(e.toErrorType()))
            } catch (e: Exception) {
                _filmsState.value = UiState.Error(errorTypeToErrorTextConverter.convert(e.toErrorType()))
            }
        }
    }
}