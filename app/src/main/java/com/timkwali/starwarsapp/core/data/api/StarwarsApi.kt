package com.timkwali.starwarsapp.core.data.api

import com.timkwali.starwarsapp.core.data.api.model.SearchResponse
import com.timkwali.starwarsapp.core.utils.Constants.PAGE_QUERY
import com.timkwali.starwarsapp.core.utils.Constants.SEARCH_ENDPOINT
import com.timkwali.starwarsapp.core.utils.Constants.SEARCH_QUERY
import retrofit2.http.GET
import retrofit2.http.Query

interface StarwarsApi {
//    https://swapi.dev/api/people/?search=r2
//    https://swapi.dev/api/people/?search=l&page=1

    @GET(SEARCH_ENDPOINT)
    suspend fun searchStarwarsApi(
        @Query(SEARCH_QUERY) searchQuery: String,
        @Query(PAGE_QUERY) page: Int
    ): SearchResponse
}