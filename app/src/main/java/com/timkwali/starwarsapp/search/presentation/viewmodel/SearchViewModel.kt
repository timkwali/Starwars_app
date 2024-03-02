package com.timkwali.starwarsapp.search.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.timkwali.starwarsapp.core.utils.ErrorType
import com.timkwali.starwarsapp.core.utils.ErrorTypeToErrorTextConverter
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.UiState
import com.timkwali.starwarsapp.search.domain.model.character.Character
import com.timkwali.starwarsapp.search.domain.usecase.SearchStarwarsApi
import com.timkwali.starwarsapp.search.presentation.events.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchStarwarsApi: SearchStarwarsApi,
    private val errorTypeToErrorTextConverter: ErrorTypeToErrorTextConverter,
): ViewModel() {

    private var _characterState: MutableState<UiState<Flow<PagingData<Character>>>> =
        mutableStateOf(UiState.Loaded(emptyFlow()))
    val characterState: State<UiState<Flow<PagingData<Character>>>> = _characterState

    fun onEvent(event: SearchEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when(event) {
                is SearchEvent.SearchCharacters -> searchCharacters(event.searchQuery)
            }
        }
    }

    private suspend fun searchCharacters(searchQuery: String) {
        _characterState.value = UiState.Loading()
        delay(300)
        _characterState.value = when(val resource = searchStarwarsApi.invoke(searchQuery)) {
            is Resource.Success -> UiState.Loaded(resource.data.cachedIn(viewModelScope))
            is Resource.Error -> UiState.Error(errorTypeToErrorTextConverter.convert(resource.error))
        }
    }

    fun setCharacterErrorState(errorType: ErrorType) {
        _characterState.value = UiState.Error(errorTypeToErrorTextConverter.convert(errorType))
    }
}