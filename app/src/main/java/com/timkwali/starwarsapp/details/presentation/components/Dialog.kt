package com.timkwali.starwarsapp.details.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.timkwali.starwarsapp.core.presentation.theme.Orange


@Composable
fun AppDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = { Icon(icon, contentDescription = "Example Icon", tint = Orange) },
        title = { Text(text = dialogTitle, style = typography.titleMedium) },
        text = { Text(text = dialogText, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
        onDismissRequest = {
            onDismissRequest()
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest()}
            ) { Text("Dismiss", style = typography.titleSmall, color = Orange) }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmation()}
            ) { Text("Confirm", style = typography.titleSmall, color = Orange) }
        },
    )
}