package com.timkwali.starwarsapp.details.presentation.events


sealed class CharacterDetailsEvent {
    data class GetCharacterDetails(val characterId: String): CharacterDetailsEvent()
    data class GetSpecies(val species: List<String>): CharacterDetailsEvent()
    data class GetHomeWorld(val homeWorldUrl: String): CharacterDetailsEvent()
    data class GetFilms(val filmsUrl: List<String>): CharacterDetailsEvent()
}