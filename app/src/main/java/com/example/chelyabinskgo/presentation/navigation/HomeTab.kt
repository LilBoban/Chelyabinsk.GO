package com.example.chelyabinskgo.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.chelyabinskgo.R
import com.example.chelyabinskgo.presentation.viewmodel.EventsViewModel
import com.example.chelyabinskgo.presentation.viewmodel.PlacesViewModel
import com.example.chelyabinskgo.ui.theme.ChelyabinskCream
import com.example.chelyabinskgo.ui.theme.ChelyabinskGreen
import org.koin.androidx.compose.koinViewModel

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
        val tabNavigator = LocalTabNavigator.current

        val context = LocalContext.current

        val placesViewModel: PlacesViewModel = koinViewModel(viewModelStoreOwner = context as ViewModelStoreOwner)
        val eventsViewModel: EventsViewModel = koinViewModel(viewModelStoreOwner = context as ViewModelStoreOwner)

        HomeScreenContent(
            onCategoryClick = { targetTab, categoryName ->
                when (targetTab) {
                    is PlacesTab -> {
                        placesViewModel.selectCategory(categoryName)
                        tabNavigator.current = PlacesTab
                    }
                    is EventsTab -> {
                        eventsViewModel.selectCategory(categoryName)
                        tabNavigator.current = EventsTab
                    }
                }
            }
        )
    }
}

@Composable
fun HomeScreenContent(
    onCategoryClick: (Tab, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChelyabinskCream)
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
        //SearchBar()

        Spacer(modifier = Modifier.height(32.dp))

        // Категории (Иконки)
        CategoriesGrid(onCategoryClick)

        Spacer(modifier = Modifier.height(60.dp))

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
fun CategoriesGrid(
    onCategoryClick: (Tab, String) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CategoryItem(icon = Icons.Outlined.Info, title = "Где\nпоесть", onClick = { onCategoryClick(PlacesTab, "Где поесть") }) // Заменить иконку на пиццу
            CategoryItem(icon = Icons.Outlined.Face, title = "Чем\nзаняться", onClick = { onCategoryClick(EventsTab, "Выставки") }) // Заменить на лампочку
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CategoryItem(icon = Icons.Outlined.Place, title = "Куда\nпойти", onClick = { onCategoryClick(PlacesTab, "Куда пойти") }) // Заменить на человечка
            CategoryItem(icon = Icons.Outlined.Home, title = "Где\nжить", onClick = {onCategoryClick(PlacesTab, "Где разместиться")} )// Тоже
            //CategoryItem(icon = Icons.Outlined.Call, title = "Сервисы") // Заменить на телефон
        }
    }
}

@Composable
fun CategoryItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
        ) {
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
    HomeScreenContent(onCategoryClick = {tab, string ->})
}