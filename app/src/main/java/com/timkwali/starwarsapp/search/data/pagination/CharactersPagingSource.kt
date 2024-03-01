package com.timkwali.starwarsapp.search.data.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.timkwali.starwarsapp.core.data.remote.StarwarsApi
import com.timkwali.starwarsapp.core.data.remote.model.response.search.Result
import com.timkwali.starwarsapp.core.utils.Constants.FIRST_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(
    private val searchQuery: String,
    private val starwarsApi: StarwarsApi,
): PagingSource<Int, Result>() {

    init {
        Log.d("98454jfa", "in init")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            Log.d("98454jfa", "inload")
            val position = params.key ?: FIRST_PAGE_INDEX
            val results: List<Result>
            val response = starwarsApi.searchStarwarsApi(searchQuery, position)

            if(response.isSuccessful) {
                results = response.body()?.results?.filterNotNull() ?: emptyList()
            } else {
                throw IOException()
            }

            val nextKey = if (results.isEmpty()) null else position + 1
            LoadResult.Page(
                data = results,
                prevKey = if (position == FIRST_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition

//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
    }

}