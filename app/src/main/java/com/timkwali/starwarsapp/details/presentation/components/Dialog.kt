package com.timkwali.starwarsapp.details.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.timkwali.starwarsapp.R
import com.timkwali.starwarsapp.core.presentation.theme.Black
import com.timkwali.starwarsapp.core.presentation.theme.Grey
import com.timkwali.starwarsapp.core.presentation.theme.Orange
import com.timkwali.starwarsapp.core.presentation.theme.White


@Composable
fun AppDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    isVisible: Boolean = true
) {
    if(isVisible) {
        AlertDialog(
            icon = { Icon(icon, contentDescription = "Example Icon", tint = Orange) },
            title = {
                Text(text = dialogTitle,
                    style = typography.titleMedium,
                    color = White
                )
            },
            text = {
                Text(text = dialogText,
                    textAlign = TextAlign.Center,
                    style = typography.bodyMedium,
                    color = Grey,
                    modifier = Modifier.fillMaxWidth())

            },
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
                    onClick = { onConfirmation() }
                ) { Text("Confirm", style = typography.titleSmall, color = Orange) }
            },
            containerColor = Black,
        )
    }
}