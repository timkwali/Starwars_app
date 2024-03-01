package com.timkwali.starwarsapp.search.domain.model.character


data class Character(
    val url: String,
    val name: String,
    val birthYear: String,

) {
    val id: String get() =
        url.removeSuffix("/")
            .split("/")
            .last()
}
