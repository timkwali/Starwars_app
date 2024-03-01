package com.timkwali.starwarsapp.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.timkwali.starwarsapp.core.data.remote.StarwarsApi
import com.timkwali.starwarsapp.core.utils.Constants
import com.timkwali.starwarsapp.search.data.pagination.CharactersPagingSource
import com.timkwali.starwarsapp.search.domain.model.character.Character
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val starwarsApi: StarwarsApi
): SearchRepository {

    override fun searchStarwarsApi(searchQuery: String): Flow<PagingData<Character>> {

        return Pager(
            config = PagingConfig(
                pageSize = Constants.PER_PAGE,
                prefetchDistance = 4,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersPagingSource(
                    searchQuery = searchQuery,
                    starwarsApi = starwarsApi,
                )
            }
        ).flow
    }
}
