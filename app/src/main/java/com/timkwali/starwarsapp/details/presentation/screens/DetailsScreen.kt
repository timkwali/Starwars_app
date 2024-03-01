package com.timkwali.starwarsapp.details.presentation.screens

import android.graphics.drawable.Icon
import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.timkwali.starwarsapp.R
import com.timkwali.starwarsapp.core.presentation.components.SimpleSnackbar
import com.timkwali.starwarsapp.core.presentation.theme.Black
import com.timkwali.starwarsapp.core.presentation.theme.Grey
import com.timkwali.starwarsapp.core.presentation.theme.Orange
import com.timkwali.starwarsapp.core.presentation.theme.White
import com.timkwali.starwarsapp.core.utils.UiState
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetails
import com.timkwali.starwarsapp.details.domain.model.film.Film
import com.timkwali.starwarsapp.details.domain.model.species.Species
import com.timkwali.starwarsapp.details.presentation.DetailComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navigateBack: () -> Unit,
    characterId: String,
    characterDetails: CharacterDetails?,
    getCharacterDetails: (characterId: String) -> Unit,
    getSpecies: (speciesUrl: List<String>) -> Unit,
    getHomeWorld: (homeWorldUrl: String) -> Unit,
    getFilms: (films: List<String>) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
//        LaunchedEffect(key1 = characterId) {
//            getCharacterDetails(characterId)
//        }
//        LaunchedEffect(key1 = characterDetails?.speciesUrl) {
//            getSpecies(characterDetails?.speciesUrl ?: emptyList())
//        }
//        LaunchedEffect(key1 = characterDetails?.homeWorldUrl) {
//            getHomeWorld(characterDetails?.homeWorldUrl ?: "")
//        }
//        LaunchedEffect(key1 = characterDetails?.filmsUrl) {
//            getFilms(characterDetails?.filmsUrl ?: emptyList())
//        }

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
//                if(searchState is UiState.Loading) {
//                    CircularProgressIndicator(color = Orange)
//                }

                Spacer(modifier = Modifier.height(5.dp))

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
                        Text(text = "Character Details", style = typography.titleLarge, color = White)
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
                            DetailComponent(title = "Name", value = characterDetails?.name ?: "Unknown")
                            DetailComponent(title = "Height", value = characterDetails?.height ?: "Unknown")
                            DetailComponent(title = "Birth Year", value = characterDetails?.birthYear ?: "Unknown")
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
                            DetailComponent(title = "Home World", value = characterDetails?.homeWorldName ?: "Unknown")
                            DetailComponent(title = "Population", value = characterDetails?.population ?: "Unknown")
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
                    val species = listOf(
                        Species("dasfadf", "Tarok"),
                        Species("dasfadf", "Tarok"),
                        Species("dasfadf", "Tarok"),
                        Species("dasfadf", "Tarok"),
                        Species("dasfadf", "Tarok"),
                        Species("dasfadf", "Tarok"),
                        Species("dasfadf", "Tarok"),
                    )
                    Text(text = "Species", style = typography.titleMedium, color = Orange, modifier = Modifier.padding(start = 20.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyRow(
                        modifier = Modifier,
                        contentPadding = PaddingValues(horizontal = 18.dp)
                    ) {
//                        items(items = characterDetails?.species ?: emptyList()) {
                        items(items = species) {
                            Row(
                                Modifier
                                    .width(250.dp)
                                    .height(150.dp)
                                    .padding(all = 5.dp)
                                    .background(Grey.copy(0.5f), shape = RoundedCornerShape(10.dp))
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
//                            Spacer(modifier = Modifier.width(5.dp))
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
                    Text(text = "Films", style = typography.titleMedium, color = Orange, modifier = Modifier.padding(start = 20.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    val films = listOf(
                        Film("movie", "fadfdfkdfdfdsafdfdsfdfdfdaffdsfdsfdfdfdfsdfsdfdsfsdfdsfsdfdsfdsfsdfdsfdsfsffdsfdffsfffsffsf"),
                        Film("movie", "fadfdfkdfdfdsafdfdsfdfdfdaffdsfdsfdfdfdfsdfsdfdsfsdfdsfsdfdsfdsfsdfdsfdsfsffdsfdffsfffsffsf"),
                        Film("movie", "fadfdfkdfdfdsafdfdsfdfdfdaffdsfdsfdfdfdfsdfsdfdsfsdfdsfsdfdsfdsfsdfdsfdsfsffdsfdffsfffsffsf"),
                        Film("movie", "fadfdfkdfdfdsafdfdsfdfdfdaffdsfdsfdfdfdfsdfsdfdsfsdfdsfsdfdsfdsfsdfdsfdsfsffdsfdffsfffsffsf"),
                    )
                    LazyRow(
                        modifier = Modifier,
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
//                        items(items = characterDetails?.films ?: emptyList()) {
                        items(items = films) {
                            Column(
                                Modifier
                                    .width(200.dp)
                                    .height(300.dp)
                                    .border(
                                        color = Color.Transparent,
                                        width = 0.dp,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .background(Grey.copy(0.5f), shape = RoundedCornerShape(10.dp))
                                    .padding(all = 8.dp),
                            ) {
                                Text(text = it.title, style = typography.titleMedium, color = White, maxLines = 2)
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(text = it.openingCrawl, style = typography.bodyMedium, color = Grey)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }

                if(characterDetails != null) {
                    Text(text = "name-> ${characterDetails.name}")
                    Text(text = "birthYear-> ${characterDetails.birthYear}")
                    Text(text = "height-> ${characterDetails.height}")
                    Text(text = "homeWorld-> ${characterDetails.homeWorldName}")
                    Text(text = "population-> ${characterDetails.population}")
                    Text(text = "species-> ${characterDetails.species}")
                    Text(text = "films-> ${characterDetails.films}")
                }

//                if(searchState is UiState.Error) {
//                    Log.d("98454jfa", "UiError->${searchState.error}")
//                    SimpleSnackbar(
//                        message = searchState.error,
//                        isVisible = dismissError
//                    )
//                }
            }
        }
    }
}