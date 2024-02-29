package com.timkwali.starwarsapp.core.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.timkwali.starwarsapp.core.presentation.navigation.AppNavHost
import com.timkwali.starwarsapp.search.presentation.viewmodel.SearchViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AppNavHost(navController = navController)
    }
}