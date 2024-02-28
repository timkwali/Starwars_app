package com.timkwali.starwarsapp.search.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.timkwali.starwarsapp.core.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Text(text = "Search", modifier = Modifier.clickable { navController.navigate(Screen.Details.route) })
            TextField(value = "", onValueChange = {

            })
        }
    }
}