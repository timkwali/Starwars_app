package com.timkwali.starwarsapp.search.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.timkwali.starwarsapp.search.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SearchScreen(
    searchState: Flow<PagingData<Character>>,
    navigateToDetailsScreen: () -> Unit,
    searchCharacters: (searchQuery: String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Button(
                onClick = {
                    searchCharacters("l")
                }) {
                Text("Search")
            }

            val lazyPagingItems = searchState.collectAsLazyPagingItems()

            LazyColumn {
                items(
                    lazyPagingItems.itemCount,
                    key = lazyPagingItems.itemKey { it }
                ) { index ->

                    val item = lazyPagingItems[index]
                    Log.d("dfkaff", "----->$item")
                    Text("Item is ${item?.name}")
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}