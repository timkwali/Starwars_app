package com.timkwali.starwarsapp.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.timkwali.starwarsapp.core.utils.ErrorTypeToErrorTextConverter
import com.timkwali.starwarsapp.core.utils.InternetConnection
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.UiState
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.search.domain.model.character.Character
import com.timkwali.starwarsapp.search.domain.usecase.SearchStarwarsApi
import com.timkwali.starwarsapp.search.presentation.events.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchStarwarsApi: SearchStarwarsApi,
    private val errorTypeToErrorTextConverter: ErrorTypeToErrorTextConverter,
    private val internetConnection: InternetConnection
): ViewModel() {

    private var _characterState: MutableStateFlow<UiState<Flow<PagingData<Character>>>> =
        MutableStateFlow(UiState.Loaded(emptyFlow()))
    val characterState: StateFlow<UiState<Flow<PagingData<Character>>>> get() = _characterState

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.SearchCharacters -> searchCharacters(event.searchQuery)
        }
    }

    private fun searchCharacters(searchQuery: String) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(!internetConnection.isInternetAvailable()) {
                throw IOException()
            } else {
                _characterState.value = UiState.Loading()
                delay(300)
                _characterState.value = when(val resource = searchStarwarsApi.invoke(searchQuery)) {
                    is Resource.Success -> UiState.Loaded(resource.data.cachedIn(viewModelScope))
                    is Resource.Error -> UiState.Error(errorTypeToErrorTextConverter.convert(resource.error))
                }
            }
        } catch (e: IOException) {
            _characterState.value = UiState.Error(errorTypeToErrorTextConverter.convert(e.toErrorType()))
        } catch (e: Exception) {
            _characterState.value = UiState.Error(errorTypeToErrorTextConverter.convert(e.toErrorType()))
        }
    }
}