package com.timkwali.starwarsapp.search.data.repository

import com.timkwali.starwarsapp.core.data.remote.StarwarsApi
import com.timkwali.starwarsapp.core.data.remote.model.response.search.SearchResponse
import com.timkwali.starwarsapp.core.utils.ErrorType
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import com.timkwali.starwarsapp.core.utils.getTestResponseBody
import com.timkwali.starwarsapp.core.utils.startPage
import com.timkwali.starwarsapp.core.utils.testSearchQuery
import com.timkwali.starwarsapp.core.utils.testSearchResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import java.io.IOException

class SearchRepositoryImplTest {

    @Test
    fun `test search starwars api returns successful result`() = runTest {
        val starwarsApi: StarwarsApi = Mockito.mock(StarwarsApi::class.java)
        Mockito.`when`(starwarsApi.searchStarwarsApi(testSearchQuery, startPage))
            .thenReturn(Response.success(testSearchResponse))

        val searchRepository: SearchRepository = SearchRepositoryImpl(starwarsApi)

        val actualResponse = searchRepository.searchStarwarsApi(testSearchQuery, startPage)
        val expectedResponse = flowOf(Resource.Success(testSearchResponse))

        assertEquals(expectedResponse.first(), actualResponse.first())
    }

    @Test
    fun `test search starwars api returns correct http error`() = runTest {
        val starwarsApi: StarwarsApi = Mockito.mock(StarwarsApi::class.java)
        Mockito.`when`(starwarsApi.searchStarwarsApi(testSearchQuery, startPage))
            .thenReturn(Response.error(501, getTestResponseBody()))

        val searchRepository: SearchRepository = SearchRepositoryImpl(starwarsApi)

        val actualResponse = searchRepository.searchStarwarsApi(testSearchQuery, startPage)
        val expectedResponse = flowOf<Resource<SearchResponse>>(Resource.Error(ErrorType.Api.Server))

        assertEquals(expectedResponse.first(), actualResponse.first())
    }
}