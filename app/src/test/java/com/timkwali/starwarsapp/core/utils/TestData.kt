package com.timkwali.starwarsapp.core.utils

import androidx.paging.PagingData
import com.google.gson.annotations.SerializedName
import com.timkwali.starwarsapp.core.data.remote.model.response.details.CharacterDetailsResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.search.Result
import com.timkwali.starwarsapp.core.data.remote.model.response.search.SearchResponse
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetails
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetailsMapper
import com.timkwali.starwarsapp.details.domain.model.film.Film
import com.timkwali.starwarsapp.details.domain.model.species.Species
import com.timkwali.starwarsapp.search.domain.model.character.Character
import kotlinx.coroutines.flow.flowOf
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

const val testSearchQuery = "luke"

val testCharacterList = listOf(
    Character(url = "https://swapi.dev/api/people/1/", name = "Luke Skywalker", birthYear = "175"),
    Character(url = "https://swapi.dev/api/people/2/", name = "Darth Vader", birthYear = "45"),
    Character(url = "https://swapi.dev/api/people/3/", name = "R2D2", birthYear = "32"),
)

val testCharacterPagingData = flowOf(PagingData.from(testCharacterList))


val testResults = listOf(
    Result(birthYear = "1234", created = "344", edited = "3545", eyeColor = "blue", films = listOf("film1", "film2"), gender = "male", hairColor = "red", height = "455", homeworld = "mars", mass = "199", name  ="Anikin", skinColor = "black", species = listOf("human"), starships = listOf("starship1"), url = "urlss", vehicles = listOf("vehicle")),
    Result(birthYear = "1234", created = "344", edited = "3545", eyeColor = "blue", films = listOf("film1", "film2"), gender = "male", hairColor = "red", height = "455", homeworld = "mars", mass = "199", name  ="Anikin", skinColor = "black", species = listOf("human"), starships = listOf("starship1"), url = "urlss", vehicles = listOf("vehicle")),
    Result(birthYear = "1234", created = "344", edited = "3545", eyeColor = "blue", films = listOf("film1", "film2"), gender = "male", hairColor = "red", height = "455", homeworld = "mars", mass = "199", name  ="Anikin", skinColor = "black", species = listOf("human"), starships = listOf("starship1"), url = "urlss", vehicles = listOf("vehicle")),
    Result(birthYear = "1234", created = "344", edited = "3545", eyeColor = "blue", films = listOf("film1", "film2"), gender = "male", hairColor = "red", height = "455", homeworld = "mars", mass = "199", name  ="Anikin", skinColor = "black", species = listOf("human"), starships = listOf("starship1"), url = "urlss", vehicles = listOf("vehicle")),
)

const val pageLoadSize = 4
const val totalPage = 4
const val startPage = 1

val testSearchResponse = SearchResponse(
    next = "$startPage",
    previous = null,
    results = testResults,
    count = testResults.size
)

fun getTestHttpException(code: Int) = HttpException(
    Response.error<ResponseBody>(
    code,
    ResponseBody.create("plain/text".toMediaTypeOrNull(), "")
))

fun getTestResponseBody(): ResponseBody  {
    return ResponseBody.create(
        "plain/text".toMediaTypeOrNull(),
        ""
    )
}

val testCharacterDetailsResponse = CharacterDetailsResponse(
    birthYear = "456",
    created = "2/2/2024",
    edited = "2/2/2024",
    eyeColor = "pink",
    films = listOf("film1"),
    gender = "male",
    hairColor = "black",
    height = "566",
    homeworld = "Munn",
    mass = "3545",
    name = "Oug",
    skinColor = "red",
    species = listOf("Munn"),
    starships = listOf("ufo"),
    url = "www.test.com/1/",
    vehicles = listOf("vehicle")

)

suspend fun getTestCharacterDetails(): CharacterDetails {
    return CharacterDetailsMapper().mapToDomain(testCharacterDetailsResponse)
}

