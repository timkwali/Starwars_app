package com.timkwali.starwarsapp.search.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.timkwali.starwarsapp.search.domain.model.Character
import com.timkwali.starwarsapp.search.domain.usecase.SearchStarwarsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchStarwarsApi: SearchStarwarsApi
): ViewModel() {

    var ss: Flow<PagingData<Character>> = flowOf()

    private var _searchState: MutableStateFlow<PagingData<Character>?> = MutableStateFlow(null)
    val searchState: StateFlow<PagingData<Character>?> get() = _searchState

    fun searchCharacters(searchQuery: String) = viewModelScope.launch(Dispatchers.IO){
        Log.d("dfkaff", "----->sent")
        ss = searchStarwarsApi
            .invoke(searchQuery)
            .cachedIn(viewModelScope)
    }
}