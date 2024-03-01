package com.timkwali.starwarsapp.search.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.timkwali.starwarsapp.R
import com.timkwali.starwarsapp.core.presentation.components.SimpleSnackbar
import com.timkwali.starwarsapp.core.presentation.theme.Grey
import com.timkwali.starwarsapp.core.presentation.theme.Orange
import com.timkwali.starwarsapp.core.presentation.theme.White
import com.timkwali.starwarsapp.core.utils.UiState
import com.timkwali.starwarsapp.search.domain.model.character.Character
import com.timkwali.starwarsapp.search.presentation.components.CharacterListItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    searchState: UiState<Flow<PagingData<Character>>>,
    searchValue: String,
    onSearchChange: (newSearchValue: String) -> Unit,
    navigateToDetailsScreen: (id: String) -> Unit,
    searchCharacters: (searchQuery: String) -> Unit
) {
    var showError by rememberSaveable { mutableStateOf(false) }
    val controller = LocalSoftwareKeyboardController.current
    var showLoading by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = searchState) {
        showLoading = searchState is UiState.Loading
        if(searchState is UiState.Error) {
            showError = true
            delay(3000)
            showError = false
        }
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = "background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star_wars),
                        contentDescription = "starwars logo",
                        modifier = Modifier
                            .width(200.dp)
                            .height(150.dp),
                    )
                    TextField(
                        value = searchValue,
                        onValueChange = { onSearchChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .border(width = 1.dp, color = Orange, shape = RoundedCornerShape(10.dp))
                            .background(Color.Transparent),
                        trailingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = "search icon", tint = Grey) },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            cursorColor = Orange,
                            textColor = White,

                            ),
                        placeholder = { Text(text = "Search Characters", style = typography.bodyLarge, color = Grey) },
                        maxLines = 1,
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                controller?.hide()
                                searchCharacters(searchValue)
                            }
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
                    )

                    if(searchState is UiState.Loaded) {
                        val lazyPagingItems = searchState.data.collectAsLazyPagingItems()

                        Spacer(modifier = Modifier.height(20.dp))
                        LazyColumn {
                            items(
                                lazyPagingItems.itemCount
                            ) { index ->
                                val item = lazyPagingItems[index]
                                if(item != null) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    CharacterListItem(character = item) {
                                        navigateToDetailsScreen(it.id)
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        }
                    }
                }

                if(searchState is UiState.Error) {
                    SimpleSnackbar(
                        message = searchState.error.asString(),
                        isVisible = showError
                    )
                }
            }

            if(showLoading) {
                CircularProgressIndicator(color = Orange)
            }
        }
    }
}