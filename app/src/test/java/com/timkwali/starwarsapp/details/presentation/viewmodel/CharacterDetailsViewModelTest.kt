package com.timkwali.starwarsapp.details.presentation.viewmodel

import app.cash.turbine.test
import com.timkwali.starwarsapp.R
import com.timkwali.starwarsapp.core.utils.BaseTest
import com.timkwali.starwarsapp.core.utils.ErrorText
import com.timkwali.starwarsapp.core.utils.ErrorType
import com.timkwali.starwarsapp.core.utils.ErrorTypeToErrorTextConverter
import com.timkwali.starwarsapp.core.utils.InternetConnection
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.UiState
import com.timkwali.starwarsapp.core.utils.getTestCharacterDetails
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.core.utils.toException
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetails
import com.timkwali.starwarsapp.details.domain.usecase.GetCharacterDetails
import com.timkwali.starwarsapp.details.domain.usecase.GetFilm
import com.timkwali.starwarsapp.details.domain.usecase.GetHomeWorld
import com.timkwali.starwarsapp.details.domain.usecase.GetSpecies
import com.timkwali.starwarsapp.details.presentation.events.CharacterDetailsEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.spy
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CharacterDetailsViewModelTest: BaseTest() {

    private lateinit var characterDetailsViewModel: CharacterDetailsViewModel
    @Mock private lateinit var getCharacterDetails: GetCharacterDetails
    @Mock  private lateinit var getSpecies: GetSpecies
    @Mock  private lateinit var getHomeWorld: GetHomeWorld
    @Mock  private lateinit var getFilm: GetFilm
    @Mock private lateinit var errorTypeToErrorTextConverter: ErrorTypeToErrorTextConverter
    @Mock private lateinit var internetConnection: InternetConnection

    @Before
    override fun before() {
        super.before()
        MockitoAnnotations.openMocks(this)
        internetConnection = Mockito.mock(InternetConnection::class.java)
        characterDetailsViewModel = spy(CharacterDetailsViewModel(
            getCharacterDetails, getSpecies, getHomeWorld,
            getFilm, errorTypeToErrorTextConverter, internetConnection
        ))
    }

    @After
    override fun after() {
        super.after()
        Mockito.clearAllCaches()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getCharacterDetails returns correct data`() = runBlockingTest {

        Mockito.`when`(internetConnection.isInternetAvailable())
            .thenReturn(true)
        Mockito.`when`(getCharacterDetails.invoke("1"))
            .thenReturn(flowOf(Resource.Success(getTestCharacterDetails())))
        characterDetailsViewModel.onEvent(CharacterDetailsEvent.GetCharacterDetails("1"))

        advanceUntilIdle()

        val expectedResult = UiState.Loaded(getTestCharacterDetails())

        characterDetailsViewModel.characterDetails.test {
            assertEquals(expectedResult, awaitItem())
        }
    }
}