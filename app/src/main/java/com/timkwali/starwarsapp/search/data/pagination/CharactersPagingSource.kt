package com.timkwali.starwarsapp.search.data.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.timkwali.starwarsapp.core.data.api.StarwarsApi
import com.timkwali.starwarsapp.core.data.api.model.Result
import com.timkwali.starwarsapp.core.utils.Constants.FIRST_PAGE_INDEX
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(
    private val searchQuery: String,
    private val searchRepository: SearchRepository,
): PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: FIRST_PAGE_INDEX
        return try {
            var results: List<Result> = emptyList()
            withContext(Dispatchers.IO) {
                this.async {
                    searchRepository.searchStarwarsApi(searchQuery, 1).collect {
                        results = it
                    }
                }.await()
            }

            val nextKey = if (results.isEmpty()) null else position + 1
            LoadResult.Page(
                data = results,
                prevKey = if (position == FIRST_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
//        return state.anchorPosition

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}