package com.timkwali.starwarsapp.details.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkwali.starwarsapp.core.utils.Constants.BASE_URL
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetails
import com.timkwali.starwarsapp.details.domain.model.film.Film
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetails: GetCharacterDetails,
    private val getSpecies: GetSpecies,
    private val getHomeWorld: GetHomeWorld,
    private val getFilm: GetFilm
): ViewModel() {

    private var _characterDetails: MutableStateFlow<CharacterDetails?> = MutableStateFlow(null)
    val characterDetails: StateFlow<CharacterDetails?> get() = _characterDetails

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
        getCharacterDetails.invoke(characterId).collect {
            _characterDetails.value = it
        }
    }

    private suspend fun getSpecies(species: List<String>) {
        species.forEach {
            if(it.isNotEmpty()) {
                withContext(Dispatchers.IO) {
                    val endpoint = it.substring(BASE_URL.length)
                    getSpecies.invoke(endpoint).collect {
                        val newList: MutableList<Species> = _characterDetails.value?.species ?: mutableListOf()
                        newList.add(it)
                        _characterDetails.value = _characterDetails.value?.copy(
                            species = newList
                        )
                    }
                }
            }
        }
    }

    private suspend fun getHomeWorld(homeWorldUrl: String) {
        if(homeWorldUrl.isNotEmpty()) {
            val endpoint = homeWorldUrl.substring(BASE_URL.length)
            getHomeWorld.invoke(endpoint).collect {
                _characterDetails.value = _characterDetails.value?.copy(
                    homeWorldName = it.name,
                    population = it.population
                )
            }
        }

    }

    private suspend fun getFilms(filmsUrl: List<String>) {
        filmsUrl.forEach {
            Log.d("90854ojk", "filmUrl--->$it")
            if(it.isNotEmpty()) {
                withContext(Dispatchers.IO) {
                    val endpoint = it.substring(BASE_URL.length)
                    getFilm.invoke(endpoint).collect {
                        val newList: MutableList<Film> = _characterDetails.value?.films ?: mutableListOf()
                        newList.add(it)
                        Log.d("90854ojk", "film--->$newList")
                        _characterDetails.value = _characterDetails.value?.copy(
                            films = newList
                        )
                    }
                }
            }
        }
    }
}