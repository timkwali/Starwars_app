package com.timkwali.starwarsapp.search.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.timkwali.starwarsapp.core.presentation.theme.Orange
import com.timkwali.starwarsapp.core.presentation.theme.White
import com.timkwali.starwarsapp.search.domain.model.character.Character

@Composable
fun CharacterListItem(
    modifier: Modifier = Modifier,
    character: Character,
    onItemClick: (character: Character) -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onItemClick(character) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = character.name,
            style = typography.titleMedium, 
            color = Orange, 
            modifier = Modifier.weight(1f),
            maxLines = 1,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = character.birthYear, style = typography.bodyMedium, color = White)
    }
}