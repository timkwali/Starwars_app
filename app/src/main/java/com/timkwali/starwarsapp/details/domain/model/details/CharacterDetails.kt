package com.timkwali.starwarsapp.details.domain.model.details

import com.timkwali.starwarsapp.details.domain.model.film.Film
import com.timkwali.starwarsapp.details.domain.model.species.Species

data class CharacterDetails(
    val name: String,
    val birthYear: String,
    val height: String,
    val speciesUrl: List<String>,
    val filmsUrl: List<String>,
    val homeWorldUrl: String,
    val species: MutableList<Species>,
    val homeWorldName: String,
    val population: String,
    val films: MutableList<Film>
)
