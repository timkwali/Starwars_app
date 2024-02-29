package com.timkwali.starwarsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.timkwali.starwarsapp.core.presentation.screen.MainScreen
import com.timkwali.starwarsapp.core.presentation.theme.StarwarsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarwarsAppTheme {
                MainScreen()
            }
        }
    }
}
