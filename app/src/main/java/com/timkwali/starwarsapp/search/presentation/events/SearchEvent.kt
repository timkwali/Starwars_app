package com.timkwali.starwarsapp.search.presentation.events

sealed class SearchEvent {
    data class SearchCharacters(val searchQuery: String) : SearchEvent()
}