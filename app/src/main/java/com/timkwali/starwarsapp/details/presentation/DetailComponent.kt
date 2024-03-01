package com.timkwali.starwarsapp.details.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.timkwali.starwarsapp.core.presentation.theme.Grey
import com.timkwali.starwarsapp.core.presentation.theme.White

@Composable
fun DetailComponent(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title.capitalize(), style = MaterialTheme.typography.titleMedium, color = Grey, maxLines = 1)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = value, style = MaterialTheme.typography.titleMedium, color = White)

    }
}