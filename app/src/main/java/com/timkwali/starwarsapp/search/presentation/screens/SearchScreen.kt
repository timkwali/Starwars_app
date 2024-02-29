package com.timkwali.starwarsapp.search.presentation.screens

import androidx.compose.foundation.clickable
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

@Composable
fun SearchScreen(
    searchState: Flow<PagingData<Character>>,
    navigateToDetailsScreen: (id: String) -> Unit,
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
                    lazyPagingItems.itemCount
                ) { index ->

                    val item = lazyPagingItems[index]
                    if(item != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            "Item is ${item.name}",
                            modifier = Modifier
                                .clickable { navigateToDetailsScreen(item.id) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}