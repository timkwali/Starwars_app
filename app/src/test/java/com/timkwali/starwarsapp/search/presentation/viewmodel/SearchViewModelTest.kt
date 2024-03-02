package com.timkwali.starwarsapp.search.presentation.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.timkwali.starwarsapp.core.utils.ErrorTypeToErrorTextConverter
import com.timkwali.starwarsapp.core.utils.ErrorTypeToErrorTextConverterImpl
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.UiState
import com.timkwali.starwarsapp.search.domain.usecase.SearchStarwarsApi
import com.timkwali.starwarsapp.search.presentation.events.SearchEvent
import com.timkwali.starwarsapp.search.utils.testCharacterPagingData
import com.timkwali.starwarsapp.search.utils.testSearchQuery
import dagger.hilt.EntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@EntryPoint
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var searchViewModel: SearchViewModel

    @Inject
    lateinit var errorTypeToErrorTextConverter: ErrorTypeToErrorTextConverter

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `search characters returns a flow of paging character data`() = runTest {
        errorTypeToErrorTextConverter = ErrorTypeToErrorTextConverterImpl()
        val searchStarwarsApi = Mockito.mock(SearchStarwarsApi::class.java)
        Mockito.`when`(searchStarwarsApi.invoke(testSearchQuery)).thenReturn(
            Resource.Success(testCharacterPagingData))
        searchViewModel = SearchViewModel(searchStarwarsApi, errorTypeToErrorTextConverter)

        searchViewModel.onEvent(SearchEvent.SearchCharacters(testSearchQuery))

        delay(3000)
//        searchViewModel.characterState.wait()

        val characterState = searchViewModel.characterState.value
        val result = if(characterState is UiState.Loaded) characterState.data else emptyFlow()

        assertEquals(testCharacterPagingData.first(), result.first())

    }
}