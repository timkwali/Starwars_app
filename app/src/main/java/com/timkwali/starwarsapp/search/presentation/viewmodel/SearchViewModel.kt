package com.timkwali.starwarsapp.search.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.UiState
import com.timkwali.starwarsapp.search.domain.model.character.Character
import com.timkwali.starwarsapp.search.domain.usecase.SearchStarwarsApi
import com.timkwali.starwarsapp.search.presentation.events.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchStarwarsApi: SearchStarwarsApi
): ViewModel() {

    private var _characterState: UiState<Flow<PagingData<Character>>> =
        UiState.Loaded(MutableSharedFlow())
    val characterState: UiState<Flow<PagingData<Character>>> get() = _characterState

    fun onEvent(event: SearchEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when(event) {
                is SearchEvent.SearchCharacters -> searchCharacters(event.searchQuery)
            }
        }
    }

    private suspend fun searchCharacters(searchQuery: String) {
        try {
            searchStarwarsApi.invoke(searchQuery)
//                .distinctUntilChanged()
//                .cachedIn(viewModelScope)
                .collect {
                    Log.d("98454jfa", "resource-->${it}")
                    _characterState = when(it) {
                        is Resource.Success -> {
                            UiState.Loaded(MutableStateFlow(it.data))
                        }
                        is Resource.Error -> UiState.Error("Error message")
                        else -> UiState.Loading()
                    }
                }

        } catch (e: Throwable) {
            _characterState = UiState.Error(e.localizedMessage ?: "Error fetcing data custome")
        } catch (e: Exception) {
            _characterState = UiState.Error(e.localizedMessage ?: "Error fetcing data exc")
        }
    }
}