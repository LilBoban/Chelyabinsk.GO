package com.example.chelyabinskgo.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object PlacesTab  : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Place)
            return remember {
                TabOptions(index = 1u, title = "Места", icon = icon)
            }
        }

    @Composable
    override fun Content() {
        Text("Здесь будет список мест")
    }
}