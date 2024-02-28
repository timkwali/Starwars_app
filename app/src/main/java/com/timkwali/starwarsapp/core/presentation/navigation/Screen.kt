package com.timkwali.starwarsapp.core.presentation.navigation

sealed class Screen(
    val name: String,
    val route: String
) {
    object Search: Screen(
        name = "Search",
        route = "search"
    )
    object Details: Screen(
        name = "Details",
        route = "details"
    )
}