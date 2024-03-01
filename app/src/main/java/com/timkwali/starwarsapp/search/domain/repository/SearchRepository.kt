package com.timkwali.starwarsapp.search.domain.repository

import androidx.paging.PagingData
import com.timkwali.starwarsapp.core.data.remote.model.response.search.Result
import com.timkwali.starwarsapp.search.domain.model.character.Character
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

     fun searchStarwarsApi(
        searchQuery: String,
    ): Flow<PagingData<Character>>
}