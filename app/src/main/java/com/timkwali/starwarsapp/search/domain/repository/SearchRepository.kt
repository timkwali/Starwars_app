package com.timkwali.starwarsapp.search.domain.repository

import androidx.paging.PagingData
import com.timkwali.starwarsapp.core.data.remote.model.response.search.Result
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchStarwarsApi(
        searchQuery: String,
    ): Flow<PagingData<Result>>
}