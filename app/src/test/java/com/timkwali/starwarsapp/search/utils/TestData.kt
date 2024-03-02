package com.timkwali.starwarsapp.search.utils

import androidx.paging.PagingData
import com.google.gson.annotations.SerializedName
import com.timkwali.starwarsapp.core.data.remote.model.response.search.Result
import com.timkwali.starwarsapp.core.data.remote.model.response.search.SearchResponse
import com.timkwali.starwarsapp.search.domain.model.character.Character
import kotlinx.coroutines.flow.flowOf


val testCharacterList = listOf(
    Character(url = "https://swapi.dev/api/people/1/", name = "Luke Skywalker", birthYear = "175"),
    Character(url = "https://swapi.dev/api/people/2/", name = "Darth Vader", birthYear = "45"),
    Character(url = "https://swapi.dev/api/people/3/", name = "R2D2", birthYear = "32"),
)

val testCharacterPagingData = flowOf(PagingData.from(testCharacterList))

const val testSearchQuery = "luke"

val testResults = listOf(
    Result(birthYear = "1234", created = "344", edited = "3545", eyeColor = "blue", films = listOf("film1", "film2"), gender = "male", hairColor = "red", height = "455", homeworld = "mars", mass = "199", name  ="Anikin", skinColor = "black", species = listOf("human"), starships = listOf("starship1"), url = "urlss", vehicles = listOf("vehicle")),
    Result(birthYear = "1234", created = "344", edited = "3545", eyeColor = "blue", films = listOf("film1", "film2"), gender = "male", hairColor = "red", height = "455", homeworld = "mars", mass = "199", name  ="Anikin", skinColor = "black", species = listOf("human"), starships = listOf("starship1"), url = "urlss", vehicles = listOf("vehicle")),
    Result(birthYear = "1234", created = "344", edited = "3545", eyeColor = "blue", films = listOf("film1", "film2"), gender = "male", hairColor = "red", height = "455", homeworld = "mars", mass = "199", name  ="Anikin", skinColor = "black", species = listOf("human"), starships = listOf("starship1"), url = "urlss", vehicles = listOf("vehicle")),
)

const val

val testSearchResponse = SearchResponse(
    next = "1",
    previous = null,
    results = testResults,
    count = testResults.size
)
