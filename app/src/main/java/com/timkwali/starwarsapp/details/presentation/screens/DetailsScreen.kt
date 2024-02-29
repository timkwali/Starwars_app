package com.timkwali.starwarsapp.details.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.timkwali.starwarsapp.search.domain.model.Character

@Composable
fun DetailsScreen(
    navigateBack: () -> Unit,
    characterUrl: String
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Button(onClick = { navigateBack() }) {
                Text("go back")
            }
            Text(text = characterUrl, modifier = Modifier.clickable { navigateBack() })
        }
    }
}