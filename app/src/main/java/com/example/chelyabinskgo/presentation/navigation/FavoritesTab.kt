package com.example.chelyabinskgo.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.chelyabinskgo.R
import com.example.chelyabinskgo.presentation.screens.favorites.FavoriteEventsScreen
import com.example.chelyabinskgo.presentation.screens.favorites.FavoritePlacesScreen
import com.example.chelyabinskgo.ui.theme.ChelyabinskGreen

object FavoritesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Favorite)
            return remember {
                TabOptions(index = 3u, title = "Избранное", icon = icon)
            }
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow.parent

        FavoritesScreenContent(
            onEventsClick = { navigator?.push(FavoriteEventsScreen()) },
            onPlacesClick = { navigator?.push(FavoritePlacesScreen()) }
        )
    }
}

@Composable
fun FavoritesScreenContent(
    onEventsClick: () -> Unit,
    onPlacesClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.splash_background_pattern),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.15f,
                alignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.splash_background_pattern),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.15f,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
        ) {
            Text(
                text = "Избранное",
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            FavoriteMenuItem(text = "Мои события", onClick = onEventsClick)

            HorizontalDivider(color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)

            FavoriteMenuItem(text = "Мои места", onClick = onPlacesClick)

            HorizontalDivider(color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
        }
    }
}

@Composable
fun FavoriteMenuItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.Black
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = ChelyabinskGreen,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true,)
@Composable
fun FavoriteMenuPreview(){
    FavoritesScreenContent(
        onEventsClick = {},
        onPlacesClick = {}
    )
}