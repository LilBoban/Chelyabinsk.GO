package com.example.chelyabinskgo.presentation.screens.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.chelyabinskgo.R
import com.example.chelyabinskgo.presentation.navigation.EventCard
import com.example.chelyabinskgo.presentation.navigation.HeaderWithPatternAndFilters
import com.example.chelyabinskgo.presentation.screens.events.EventDetailsScreen
import com.example.chelyabinskgo.presentation.viewmodel.FavoriteEventsViewModel
import com.example.chelyabinskgo.ui.theme.ChelyabinskCream
import com.example.chelyabinskgo.ui.theme.ChelyabinskGreen
import org.koin.androidx.compose.koinViewModel

class FavoriteEventsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: FavoriteEventsViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.loadEvents()
        }
        Scaffold() { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
            ) {
                if (!uiState.isLoading && uiState.events.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = ChelyabinskGreen,
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.CenterStart)
                                .clickable { navigator.pop() }
                        )
                    }
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("В избранном пока пусто", color = Color.Gray)
                    }
                } else {
                    if (uiState.categories.isNotEmpty()) {
                        Header(
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
                        items(uiState.filteredEvents) { event ->
                            Box(modifier = Modifier.clickable {
                                navigator.push(EventDetailsScreen(event))
                            }) {
                                EventCard(
                                    event = event,
                                    onFavoriteClick = { viewModel.onFavoriteClick(event) }
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                thickness = 1.dp,
                                color = Color.LightGray.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
        }

    }

    @Composable
    fun Header(
        categories: List<String>,
        selectedCategory: String,
        onCategorySelected: (String) -> Unit,
        navigator: Navigator
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = R.drawable.splash_background_pattern),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.2f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clipToBounds()
                    .scale(1.3f)
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

                    Text(
                        text = "Мои события",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Поиск") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                tint = ChelyabinskGreen
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = ChelyabinskCream,
                            unfocusedContainerColor = ChelyabinskCream,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f)
                    )


                }


                Spacer(modifier = Modifier.height(32.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    items(categories) { category ->
                        val isSelected = category == selectedCategory
                        val backgroundColor =
                            if (isSelected) Color(0xFFA5D6A7) else ChelyabinskCream

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(backgroundColor)
                                .clickable { onCategorySelected(category) }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = category,
                                color = Color.Black,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}