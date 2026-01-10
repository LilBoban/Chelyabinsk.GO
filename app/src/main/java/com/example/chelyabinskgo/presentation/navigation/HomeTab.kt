package com.example.chelyabinskgo.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.chelyabinskgo.R
import com.example.chelyabinskgo.ui.theme.ChelyabinskCream
import com.example.chelyabinskgo.ui.theme.ChelyabinskGreen

object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Home)
            return remember {
                TabOptions(index = 0u, title = "Главная", icon = icon)
            }
        }

    @Composable
    override fun Content() {
        HomeScreenContent()
    }
}

@Composable
fun HomeScreenContent() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChelyabinskCream)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Заголовок
        Text(
            text = "ЧЕЛЯБИНСК.GO",
            color = ChelyabinskGreen,
            fontSize = 40.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 36.dp),
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Поиск
        SearchBar()

        Spacer(modifier = Modifier.height(32.dp))

        // Категории (Иконки)
        CategoriesGrid()

        Spacer(modifier = Modifier.height(24.dp))

        // Баннер
        PromoBanner()

        Spacer(modifier = Modifier.height(24.dp))

        // Узор внизу
        Image(
            painter = painterResource(id = R.drawable.splash_background_pattern_wide),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SearchBar() {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Поиск", color = Color.Black) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search", tint = ChelyabinskGreen)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(56.dp)
    )
}

@Composable
fun CategoriesGrid() {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CategoryItem(icon = Icons.Outlined.Info, title = "Где\nпоесть") // Заменить иконку на пиццу
            CategoryItem(icon = Icons.Outlined.Face, title = "Чем\nзаняться") // Заменить на лампочку
            CategoryItem(icon = Icons.Outlined.Home, title = "Где\nжить") // Тоже
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CategoryItem(icon = Icons.Outlined.Place, title = "Куда\nпойти") // Заменить на человечка
            CategoryItem(icon = Icons.Outlined.Call, title = "Сервисы") // Заменить на телефон
        }
    }
}

@Composable
fun CategoryItem(icon: ImageVector, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 14.sp,
            lineHeight = 16.sp
        )
    }
}

@Composable
fun PromoBanner() {
    Image(
        painter = painterResource(id = R.drawable.banner),
        contentDescription = "Баннер: Все события Челябинска",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
        // .height(140.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun HomeTabPreview() {
    HomeScreenContent()
}