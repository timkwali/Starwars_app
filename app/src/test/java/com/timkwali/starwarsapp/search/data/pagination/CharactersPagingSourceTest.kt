package com.timkwali.starwarsapp.search.data.pagination

import androidx.paging.PagingSource
import com.timkwali.starwarsapp.core.utils.EmptyResponseException
import com.timkwali.starwarsapp.core.utils.ErrorType
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.search.domain.model.character.CharacterMapper
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import com.timkwali.starwarsapp.core.utils.pageLoadSize
import com.timkwali.starwarsapp.core.utils.testSearchQuery
import com.timkwali.starwarsapp.core.utils.testSearchResponse
import com.timkwali.starwarsapp.core.utils.totalPage
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException

class CharactersPagingSourceTest {

    @Test
    fun `test item loaded with refresh`() = runTest {
        val mockSearchRepository: SearchRepository = Mockito.mock(SearchRepository::class.java)
        Mockito.`when`(mockSearchRepository.searchStarwarsApi(testSearchQuery, 1)).thenReturn(
            flowOf(Resource.Success(testSearchResponse))
        )
        val charactersPagingSource = CharactersPagingSource(testSearchQuery, mockSearchRepository)

        val refreshLoadParams = PagingSource.LoadParams.Refresh<Int>(
            key = null,
            loadSize = pageLoadSize,
            placeholdersEnabled = false
        )
        val actualLoadResult = charactersPagingSource.load(refreshLoadParams)

        val expectedLoadResultPage = PagingSource.LoadResult.Page(
            data =  testSearchResponse.results?.filterNotNull()?.map {
                CharacterMapper().mapToDomain(it)
            } ?: emptyList(),
            prevKey = null,
            nextKey = 2
        )

        assertTrue(
            actualLoadResult is PagingSource.LoadResult.Page
        )
        assertEquals(
            expectedLoadResultPage.prevKey,
            (actualLoadResult as PagingSource.LoadResult.Page).prevKey
        )
        assertEquals(
            expectedLoadResultPage.nextKey,
            actualLoadResult.nextKey
        )
        assertEquals(
            expectedLoadResultPage.data.size,
            actualLoadResult.data.size
        )
        (0 until  expectedLoadResultPage.data.size).forEach {
            assertEquals(
                expectedLoadResultPage.data[it],
                actualLoadResult.data[it]
            )
        }
    }

    @Test
    fun `test all item loaded`() = runTest {
        (1 .. totalPage).forEach { page ->
            val mockSearchRepository: SearchRepository = Mockito.mock(SearchRepository::class.java)
            Mockito.`when`(mockSearchRepository.searchStarwarsApi(testSearchQuery, page)).thenReturn(
                flowOf(Resource.Success(testSearchResponse)))
            val charactersPagingSource = CharactersPagingSource(testSearchQuery, mockSearchRepository)
            val loadParams = if(page == 1)
                PagingSource.LoadParams.Refresh(
                    key = page,
                    loadSize = pageLoadSize,
                    placeholdersEnabled = false
                )
            else
                PagingSource.LoadParams.Append(
                    key = page,
                    loadSize = pageLoadSize,
                    placeholdersEnabled = false
                )

            val actualLoadResult = charactersPagingSource.load(loadParams)
            val expectedLoadResultPage = PagingSource.LoadResult.Page(
                data =  testSearchResponse.results?.filterNotNull()?.map {
                    CharacterMapper().mapToDomain(it)
                } ?: emptyList(),
                prevKey = if (page > 1) page -1 else null,
                nextKey = if (page <= totalPage) page + 1 else null
            )

            assertTrue(actualLoadResult is PagingSource.LoadResult.Page)
            assertEquals(expectedLoadResultPage.prevKey, (actualLoadResult as PagingSource.LoadResult.Page).prevKey )
            assertEquals(expectedLoadResultPage.nextKey, actualLoadResult.nextKey )

            assertEquals(expectedLoadResultPage.data.size, actualLoadResult.data.size)
            (0 until  expectedLoadResultPage.data.size).forEach {
                assertEquals(expectedLoadResultPage.data[it], actualLoadResult.data[it])
            }
        }
    }

    @Test
    fun `test load result error with exception` () = runTest {
        val mockSearchRepository: SearchRepository = Mockito.mock(SearchRepository::class.java)
        Mockito.`when`(mockSearchRepository.searchStarwarsApi(testSearchQuery, 1)).thenReturn(
            flowOf(Resource.Error(ErrorType.Api.ServiceUnavailable))
        )

        val charactersPagingSource = CharactersPagingSource(testSearchQuery, mockSearchRepository)

        val refreshLoadParams = PagingSource.LoadParams.Refresh<Int>(
            key = null,
            loadSize = pageLoadSize,
            placeholdersEnabled = false
        )
        val loadResult = charactersPagingSource.load(refreshLoadParams)

        assertTrue(loadResult is PagingSource.LoadResult.Error)
        assertTrue((loadResult as PagingSource.LoadResult.Error).throwable is HttpException)
    }


    @Test
    fun `test load result error with response is not successful` () = runTest {
        val mockSearchRepository: SearchRepository = Mockito.mock(SearchRepository::class.java)
        Mockito.`when`(mockSearchRepository.searchStarwarsApi(testSearchQuery, 1)).thenReturn(
            flowOf(Resource.Error(ErrorType.Api.EmptyListError)))
        val charactersPagingSource = CharactersPagingSource(testSearchQuery, mockSearchRepository)

        val refreshLoadParams = PagingSource.LoadParams.Refresh<Int>(
            key = null,
            loadSize = pageLoadSize,
            placeholdersEnabled = false
        )
        val loadResult = charactersPagingSource.load(refreshLoadParams)

        assertTrue(loadResult is PagingSource.LoadResult.Error)

    }
}