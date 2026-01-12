package com.example.chelyabinskgo.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.chelyabinskgo.R
import com.example.chelyabinskgo.presentation.navigation.MainTabsScreen
import com.example.chelyabinskgo.ui.theme.ChelyabinskGreen
import com.example.chelyabinskgo.ui.theme.ChelyabinskText
import kotlinx.coroutines.delay

class SplashScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        // типа загрузка - задержка фейк
        LaunchedEffect(key1 = Unit) {
            delay(1500)
            navigator.replaceAll(MainTabsScreen())
        }
        SplashScreenContent()
    }
}

@Composable
fun SplashScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChelyabinskGreen)
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_background_pattern),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.38f)
                .scale(1.1f)
                .absoluteOffset(y = 10.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 75.dp, bottom = 49.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ЧЕЛЯБИНСК.GO",
                color = ChelyabinskText,
                fontSize = 40.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 2.sp
            )
        }

        Image(
            painter = painterResource(id = R.drawable.splash_background_pattern),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.44f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreenContent()
}