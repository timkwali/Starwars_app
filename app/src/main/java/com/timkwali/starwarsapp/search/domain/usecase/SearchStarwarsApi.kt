package com.timkwali.starwarsapp.search.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.timkwali.starwarsapp.core.data.api.model.Result
import com.timkwali.starwarsapp.core.utils.Constants.PER_PAGE
import com.timkwali.starwarsapp.search.data.pagination.CharactersPagingSource
import com.timkwali.starwarsapp.search.domain.model.Character
import com.timkwali.starwarsapp.search.domain.model.CharacterMapper
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchStarwarsApi @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(searchQuery: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = PER_PAGE,
                prefetchDistance = 5,
                enablePlaceholders = false
            )
        ) {
            CharactersPagingSource(
                searchQuery = searchQuery,
                searchRepository = searchRepository
            )
        }.flow
        .map { value: PagingData<Result> ->
            value.map {
                CharacterMapper().mapToDomain(it)
            }
        }
    }
}