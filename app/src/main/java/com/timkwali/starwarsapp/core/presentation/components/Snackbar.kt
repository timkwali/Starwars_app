package com.timkwali.starwarsapp.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleSnackbar(
    message: String,
    isVisible: Boolean,
) {
    if(isVisible) {
        Snackbar(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            content = { Text(message) }
        )
    }
}