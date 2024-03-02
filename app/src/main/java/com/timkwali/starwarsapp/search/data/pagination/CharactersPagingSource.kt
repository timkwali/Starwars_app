package com.timkwali.starwarsapp.search.data.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.timkwali.starwarsapp.core.data.remote.StarwarsApi
import com.timkwali.starwarsapp.core.data.remote.model.response.search.Result
import com.timkwali.starwarsapp.core.utils.Constants.FIRST_PAGE_INDEX
import com.timkwali.starwarsapp.core.utils.EmptyResponseException
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.search.domain.model.character.Character
import com.timkwali.starwarsapp.search.domain.model.character.CharacterMapper
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(
    private val searchQuery: String,
    private val searchRepository: SearchRepository,
): PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val position = params.key ?: FIRST_PAGE_INDEX
            var characters: List<Character> = emptyList()

            withContext(Dispatchers.IO) {
                this.async {
                    searchRepository.searchStarwarsApi(searchQuery, position).collectLatest {
                        if(it is Resource.Success) {
                            characters = it.data?.results?.map {
                                CharacterMapper().mapToDomain(it)
                            } ?: emptyList()
                        } else if(it is Resource.Error) {
                            throw Exception()
                        }
                    }
                }.await()
            }


            val nextKey = if (characters.isEmpty()) null else position + 1

            if(characters.isEmpty()) {
                throw EmptyResponseException()
            } else {
                LoadResult.Page(
                    data = characters,
                    prevKey = if (position == FIRST_PAGE_INDEX) null else position - 1,
                    nextKey = nextKey
                )
            }

        } catch (e: EmptyResponseException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
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