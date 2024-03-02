package com.timkwali.starwarsapp.search.data.pagination

import android.util.Log
import androidx.paging.PagingSource
import com.timkwali.starwarsapp.core.data.remote.model.response.search.SearchResponse
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.search.domain.model.character.CharacterMapper
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import com.timkwali.starwarsapp.search.utils.testSearchQuery
import com.timkwali.starwarsapp.search.utils.testSearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CharactersPagingSourceTest {

    private lateinit var mockSearchRepository: SearchRepository
    private lateinit var charactersPagingSource: CharactersPagingSource

    @Before
    fun setUp(): Unit = runBlocking {
        mockSearchRepository = Mockito.mock(SearchRepository::class.java)
        Mockito.`when`(mockSearchRepository.searchStarwarsApi(testSearchQuery, 1)).thenReturn(
            flowOf(Resource.Success(testSearchResponse))
        )
        charactersPagingSource = CharactersPagingSource(testSearchQuery, mockSearchRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test item loaded with refresh`() = runTest {
        val refreshLoadParams = PagingSource.LoadParams.Refresh<Int>(
            key = null,
            loadSize = 4,
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

//        println("lasffa0-0r94 ----> actual-> ${
//            (actualLoadResult as PagingSource.LoadResult.Page).data
//        }")
//        println("lasffa0-0r94 ----> expected-> ${expectedLoadResultPage.data}")

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
}