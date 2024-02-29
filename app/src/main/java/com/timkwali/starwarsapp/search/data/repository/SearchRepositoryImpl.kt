package com.timkwali.starwarsapp.search.data.repository

import com.timkwali.starwarsapp.core.data.api.StarwarsApi
import com.timkwali.starwarsapp.core.data.api.model.Result
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val starwarsApi: StarwarsApi
): SearchRepository {

    override suspend fun searchStarwarsApi(
        searchQuery: String, page: Int
    ): Flow<List<Result>>  = flow {
        emit(
            starwarsApi.searchStarwarsApi(searchQuery, page).results
                ?.filterNotNull() ?: emptyList()
        )
    }
}