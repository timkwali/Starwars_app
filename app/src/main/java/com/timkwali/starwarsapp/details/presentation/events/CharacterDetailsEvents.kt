package com.timkwali.starwarsapp.details.presentation.events


sealed class CharacterDetailsEvents {
    data class GetCharacterDetails(val characterId: String): CharacterDetailsEvents()
}