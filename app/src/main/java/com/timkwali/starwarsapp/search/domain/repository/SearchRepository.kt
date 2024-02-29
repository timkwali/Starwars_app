package com.timkwali.starwarsapp.search.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.timkwali.starwarsapp.core.data.api.model.Result
import com.timkwali.starwarsapp.core.data.api.model.SearchResponse
import com.timkwali.starwarsapp.search.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchStarwarsApi(
        searchQuery: String,
        page: Int
    ): Flow<List<Result>>
}