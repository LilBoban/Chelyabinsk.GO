package com.example.chelyabinskgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import com.example.chelyabinskgo.presentation.screens.splash.SplashScreen
import com.example.chelyabinskgo.ui.theme.ChelyabinskgoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChelyabinskgoTheme {
                Navigator(screen = SplashScreen())
            }
        }
    }
}