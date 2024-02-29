package com.timkwali.starwarsapp.search.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.timkwali.starwarsapp.search.domain.model.Character
import com.timkwali.starwarsapp.search.domain.usecase.SearchStarwarsApi
import com.timkwali.starwarsapp.search.presentation.events.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchStarwarsApi: SearchStarwarsApi
): ViewModel() {


    private var _searchState: MutableStateFlow<PagingData<Character>> = MutableStateFlow(PagingData.empty())
    val searchState: StateFlow<PagingData<Character>> get() = _searchState

    fun onEvent(event: SearchEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when(event) {
                is SearchEvent.SearchCharacters -> searchCharacters(event.searchQuery)
            }
        }
    }

    private suspend fun searchCharacters(searchQuery: String) {
        searchStarwarsApi.invoke(searchQuery)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _searchState.value = it
            }
    }
}