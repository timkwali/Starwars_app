package com.timkwali.starwarsapp.core.data.remote

import com.timkwali.starwarsapp.core.data.remote.model.response.details.CharacterDetailsResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.film.FilmResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.homeworld.HomeWorldResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.search.SearchResponse
import com.timkwali.starwarsapp.core.data.remote.model.response.species.SpeciesResponse
import com.timkwali.starwarsapp.core.utils.Constants.PAGE_QUERY
import com.timkwali.starwarsapp.core.utils.Constants.CHARACTER_ENDPOINT
import com.timkwali.starwarsapp.core.utils.Constants.SEARCH_QUERY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarwarsApi {

    @GET(CHARACTER_ENDPOINT)
    suspend fun searchStarwarsApi(
        @Query(SEARCH_QUERY) searchQuery: String,
        @Query(PAGE_QUERY) page: Int
    ): Response<SearchResponse>

    @GET("$CHARACTER_ENDPOINT/{id}")
    suspend fun getCharacterDetail(
        @Path("id") id: String
    ): CharacterDetailsResponse

    @GET("{species}")
    suspend fun getSpecies(
        @Path("species") species: String
    ): SpeciesResponse

    @GET("{homeworld}")
    suspend fun getHomeWorld(
        @Path("homeworld") homeWorld: String
    ): HomeWorldResponse

    @GET("{film}")
    suspend fun getFilm(
        @Path("film") film: String
    ): FilmResponse
}