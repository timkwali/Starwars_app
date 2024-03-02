package com.timkwali.starwarsapp.search.data.repository

import com.timkwali.starwarsapp.core.data.remote.StarwarsApi
import com.timkwali.starwarsapp.core.data.remote.model.response.search.SearchResponse
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.handleResponse
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val starwarsApi: StarwarsApi
): SearchRepository {

    override suspend fun searchStarwarsApi(searchQuery: String, page: Int): Flow<Resource<SearchResponse?>> {
        return starwarsApi.searchStarwarsApi(searchQuery, page).handleResponse()
    }
}
