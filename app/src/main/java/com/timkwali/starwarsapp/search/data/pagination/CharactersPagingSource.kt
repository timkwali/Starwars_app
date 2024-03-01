package com.timkwali.starwarsapp.search.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.timkwali.starwarsapp.core.data.remote.StarwarsApi
import com.timkwali.starwarsapp.core.utils.Constants.FIRST_PAGE_INDEX
import com.timkwali.starwarsapp.search.domain.model.character.Character
import com.timkwali.starwarsapp.search.domain.model.character.CharacterMapper
import retrofit2.HttpException
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(
    private val searchQuery: String,
    private val starwarsApi: StarwarsApi,
): PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val position = params.key ?: FIRST_PAGE_INDEX
            val results = starwarsApi.searchStarwarsApi(searchQuery, position)
                .results?.filterNotNull() ?: emptyList()
            val characters = results.map {
                CharacterMapper().mapToDomain(it)
            }

            val nextKey = if (characters.isEmpty()) null else position + 1

            LoadResult.Page(
                data = characters,
                prevKey = if (position == FIRST_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}