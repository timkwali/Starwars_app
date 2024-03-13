package com.timkwali.starwarsapp.details.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.timkwali.starwarsapp.R
import com.timkwali.starwarsapp.core.presentation.components.SimpleSnackbar
import com.timkwali.starwarsapp.core.presentation.theme.Grey
import com.timkwali.starwarsapp.core.presentation.theme.Orange
import com.timkwali.starwarsapp.core.presentation.theme.White
import com.timkwali.starwarsapp.core.utils.UiState
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetails
import com.timkwali.starwarsapp.details.domain.model.film.Film
import com.timkwali.starwarsapp.details.domain.model.homeworld.HomeWorld
import com.timkwali.starwarsapp.details.domain.model.species.Species
import com.timkwali.starwarsapp.details.presentation.DetailComponent
import com.timkwali.starwarsapp.core.presentation.components.AppDialog
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    characterId: String,
    characterDetailsState: UiState<CharacterDetails?>,
    speciesState: UiState<List<Species>>,
    filmsState: UiState<List<Film>>,
    homeWorldState: UiState<HomeWorld?>,
    isErrorState: Boolean,
    errorMessage: String,
    getCharacterDetails: (characterId: String) -> Unit,
    getSpecies: (speciesUrl: List<String>) -> Unit,
    getHomeWorld: (homeWorldUrl: String) -> Unit,
    getFilms: (films: List<String>) -> Unit,
    navigateBack: () -> Unit
) {
    var showError by rememberSaveable { mutableStateOf(false) }
    val showAlertDialog = rememberSaveable{ mutableStateOf(false) }
    val alertDialogFilm: MutableState<Film?> = rememberSaveable { mutableStateOf(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        LaunchedEffect(key1 = characterId) {
            getCharacterDetails(characterId)
        }
        LaunchedEffect(key1 = isErrorState) {
            if(isErrorState) {
                showError = true
                delay(3000)
                showError = false
            }
        }
        LaunchedEffect(key1 = characterDetailsState) {
            if(characterDetailsState is UiState.Loaded && characterDetailsState.data != null) {
                getSpecies(characterDetailsState.data?.speciesUrl ?: emptyList())
                getHomeWorld(characterDetailsState.data?.homeWorldUrl ?: "")
                getFilms(characterDetailsState.data?.filmsUrl ?: emptyList())
            }
        }


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
                        .verticalScroll(rememberScrollState()),
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Back icon",
                            tint = White,
                            modifier = Modifier
                                .size(35.dp)
                                .clickable { navigateBack() }
                        )

                        Box(modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Text(text = stringResource(id = R.string.character_details), style = typography.titleLarge, color = White)
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    Row() {
                        Spacer(modifier = Modifier.width(20.dp))
                        Column(
                            modifier = Modifier
                                .background(Grey.copy(0.5f), shape = RoundedCornerShape(10.dp))
                                .clip(RoundedCornerShape(10.dp))
                                .weight(1f)
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                if(characterDetailsState is UiState.Loaded) {
                                    DetailComponent(title = stringResource(id = R.string.name), value = characterDetailsState.data?.name ?: stringResource(id = R.string.unknown))
                                    DetailComponent(title = stringResource(id = R.string.height), value = characterDetailsState.data?.height ?: stringResource(id = R.string.unknown))
                                    DetailComponent(title = stringResource(id = R.string.birth_year), value = characterDetailsState.data?.birthYear ?: stringResource(id = R.string.unknown))
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Divider(modifier = Modifier
                                .height(1.dp)
                                .padding(horizontal = 20.dp), color = Grey)
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                if(homeWorldState is UiState.Loaded) {
                                    DetailComponent(title = stringResource(id = R.string.home_world), value = homeWorldState.data?.name ?: stringResource(id = R.string.unknown))
                                    DetailComponent(title = stringResource(id = R.string.population), value = homeWorldState.data?.population ?: stringResource(id = R.string.unknown))
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                color = Color.Transparent,
                                width = 0.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Text(text = "Species", style = typography.titleMedium, color = Orange, modifier = Modifier.padding(start = 20.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            if(speciesState is UiState.Loading) {
                                CircularProgressIndicator(color = Orange, modifier = Modifier.size(30.dp))
                            }
                        }
                        if(speciesState is UiState.Loaded) {
                            LazyRow(
                                modifier = Modifier,
                                contentPadding = PaddingValues(horizontal = 18.dp)
                            ) {
                                items(items = speciesState.data) {
                                    Row(
                                        Modifier
                                            .width(250.dp)
                                            .height(150.dp)
                                            .padding(all = 5.dp)
                                            .background(
                                                Grey.copy(0.5f),
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .clip(RoundedCornerShape(10.dp)),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        DetailComponent(title = "Name", value = it.name)
                                        Spacer(modifier = Modifier.width(5.dp))
                                        Divider(
                                            Modifier
                                                .height(50.dp)
                                                .width(1.dp), color = Grey)
                                        Spacer(modifier = Modifier.width(5.dp))
                                        DetailComponent(title = "Language", value = it.language)
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                color = Color.Transparent,
                                width = 0.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Text(text = stringResource(id = R.string.films), style = typography.titleMedium, color = Orange, modifier = Modifier.padding(start = 20.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            if(filmsState is UiState.Loading) {
                                CircularProgressIndicator(color = Orange, modifier = Modifier.size(30.dp).fillMaxWidth())
                            }
                        }
                        if(filmsState is UiState.Loaded) {
                            LazyRow(
                                modifier = Modifier,
                                contentPadding = PaddingValues(horizontal = 20.dp)
                            ) {
                                items(items = filmsState.data) {
                                    Column(
                                        Modifier
                                            .width(200.dp)
                                            .height(300.dp)
                                            .border(
                                                color = Color.Transparent,
                                                width = 0.dp,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .background(
                                                Grey.copy(0.5f),
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .padding(all = 8.dp)
                                            .clickable {
                                                showAlertDialog.value = true
                                                alertDialogFilm.value = it
                                            },
                                    ) {
                                        Text(text = it.title, style = typography.titleMedium, color = White, maxLines = 2)
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Text(text = it.openingCrawl, style = typography.bodyMedium, color = Grey, modifier = Modifier.weight(1f))
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Text(text = stringResource(id = R.string.more), style = typography.bodyLarge, color = Orange)
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                        }
                    }
                }
                SimpleSnackbar(
                    message = errorMessage,
                    isVisible = showError
                )
            }
            if(characterDetailsState is UiState.Loading) {
                CircularProgressIndicator(color = Orange)
            }
            AppDialog(
                onDismissRequest = { showAlertDialog.value = false  },
                onConfirmation = { showAlertDialog.value = false },
                dialogTitle = alertDialogFilm.value?.title ?: "",
                dialogText = alertDialogFilm.value?.openingCrawl ?: "",
                icon = Icons.Filled.Info,
                isVisible = showAlertDialog.value
            )
        }
    }
}