package com.example.chelyabinskgo.presentation.screens.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.chelyabinskgo.R
import com.example.chelyabinskgo.presentation.navigation.PlaceCard
import com.example.chelyabinskgo.presentation.navigation.PlacesHeader
import com.example.chelyabinskgo.presentation.screens.places.PlaceDetailsScreen
import com.example.chelyabinskgo.presentation.viewmodel.FavoritePlacesViewModel
import com.example.chelyabinskgo.ui.theme.ChelyabinskCardGreen
import com.example.chelyabinskgo.ui.theme.ChelyabinskCream
import com.example.chelyabinskgo.ui.theme.ChelyabinskGreen
import org.koin.androidx.compose.koinViewModel

class FavoritePlacesScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: FavoritePlacesViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.loadPlaces()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .statusBarsPadding()
        ) {

            if (!uiState.isLoading && uiState.places.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад",
                        tint = ChelyabinskGreen,
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.TopStart)
                            .clickable { navigator.pop() }
                    )
                    Text("В избранном пока нет мест", color = Color.Gray)
                }
            } else {
                if (uiState.categories.isNotEmpty()) {

                    FavPlacesHeader(
                        categories = uiState.categories,
                        selectedCategory = uiState.selectedCategory,
                        onCategorySelected = { viewModel.selectCategory(it) },
                        navigator = navigator
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.filteredPlaces) { place ->
                        Box(modifier = Modifier.clickable {
                            navigator.push(PlaceDetailsScreen(place))
                        }) {
                            PlaceCard(place)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }

    @Composable
    fun FavPlacesHeader(
        categories: List<String>,
        selectedCategory: String,
        onCategorySelected: (String) -> Unit,
        navigator: Navigator
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.splash_background_pattern),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.15f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clipToBounds()
            )

            Column(modifier = Modifier.padding(top = 16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад",
                        tint = ChelyabinskGreen,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { navigator.pop() }
                    )
                    Text("Мои места", fontSize = 32.sp, color = Color.Black)
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Поиск") },
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = ChelyabinskGreen) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = ChelyabinskCream,
                        unfocusedContainerColor = ChelyabinskCream,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        val isSelected = category == selectedCategory
                        val bgColor = if (isSelected) ChelyabinskCardGreen else ChelyabinskCream

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(bgColor)
                                .clickable { onCategorySelected(category) }
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Text(text = category, fontSize = 14.sp, color = Color.Black)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
