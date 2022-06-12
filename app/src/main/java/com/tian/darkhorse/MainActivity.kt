package com.tian.darkhorse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.tian.darkhorse.presentation.navigation.NavigationComponent
import com.tian.darkhorse.ui.theme.DarkhorseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DarkhorseTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colors.background) {
                    NavigationComponent(navController)
                }
            }
        }
    }
}