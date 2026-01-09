package com.example.chelyabinskgo.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place // Иконка для таба
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
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
import com.example.chelyabinskgo.domain.model.PlaceMock
import com.example.chelyabinskgo.presentation.viewmodel.PlacesViewModel
import com.example.chelyabinskgo.ui.theme.ChelyabinskCardGreen
import com.example.chelyabinskgo.ui.theme.ChelyabinskCream
import com.example.chelyabinskgo.ui.theme.ChelyabinskGreen
import org.koin.androidx.compose.koinViewModel

object PlacesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Outlined.Place)
            return remember {
                TabOptions(index = 2u, title = "Места", icon = icon)
            }
        }

    @Composable
    override fun Content() {
        PlacesScreenContent()
    }
}

@Composable
fun PlacesScreenContent() {
    val viewModel: PlacesViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val filteredPlaces = uiState.filteredPlaces

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            if (uiState.errorMessage != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = uiState.errorMessage.orEmpty(), color = Color.Red)
                }
            }

            PlacesHeader(
                categories = uiState.categories,
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { viewModel.selectCategory(it) }
            )
        }

        if (uiState.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading...", color = Color.Gray)
                }
            }
        } else {
            items(filteredPlaces) { place ->
                PlaceCard(place)
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (filteredPlaces.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No places found", color = Color.Gray)
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun PlacesHeader(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
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
                Text("Места", fontSize = 32.sp, color = Color.Black)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Info, null, tint = ChelyabinskGreen, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Сервисы", fontSize = 14.sp, color = Color.Black)
                }
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

@Composable
fun PlaceCard(place: PlaceMock) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        // .height(250.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Color.Gray)
            ) {
                // Image( ... )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ChelyabinskCardGreen)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = place.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = place.address,
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlacesScreenPrewiew(){
    PlacesScreenContent()
}
