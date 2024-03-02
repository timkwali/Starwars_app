package com.timkwali.starwarsapp.search.domain.repository

import com.timkwali.starwarsapp.core.data.remote.model.response.search.SearchResponse
import com.timkwali.starwarsapp.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

     suspend fun searchStarwarsApi(searchQuery: String, page: Int): Flow<Resource<SearchResponse?>>
}