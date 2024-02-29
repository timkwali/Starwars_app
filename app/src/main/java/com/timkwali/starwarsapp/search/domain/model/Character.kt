package com.timkwali.starwarsapp.search.domain.model

data class Character(
    val name: String,
    val height: String,
    val species: List<String?>,
    val language: String,
    val homeWorld: String,
    val population: String,
    val films: List<String>,
)
