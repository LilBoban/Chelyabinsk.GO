package com.example.chelyabinskgo.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.chelyabinskgo.R
import com.example.chelyabinskgo.domain.model.EventMock
import com.example.chelyabinskgo.presentation.screens.events.EventDetailsScreen
import com.example.chelyabinskgo.presentation.util.formatIsoDateTimeRu
import com.example.chelyabinskgo.presentation.viewmodel.EventsViewModel
import com.example.chelyabinskgo.ui.theme.ChelyabinskCream
import com.example.chelyabinskgo.ui.theme.ChelyabinskGreen
import coil.compose.AsyncImage
import com.example.chelyabinskgo.domain.model.mockEvents
import com.example.chelyabinskgo.presentation.viewmodel.EventsUiState
import com.example.chelyabinskgo.ui.theme.ChelyabinskCardGreen
import org.koin.androidx.compose.koinViewModel

object EventsTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.DateRange)
            return remember {
                TabOptions(index = 1u, title = "События", icon = icon)
            }
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow.parent

        val context = LocalContext.current
        val viewModel: EventsViewModel = koinViewModel(viewModelStoreOwner = context as ViewModelStoreOwner)

        val uiState by viewModel.uiState.collectAsState()

        EventsScreenContent(
            uiState = uiState,
            onCategoryClick = { category -> viewModel.selectCategory(category) },
            onEventClick = { event -> navigator?.push(EventDetailsScreen(event)) },
            onFavoriteClick = { event -> viewModel.onFavoriteClick(event) },
            onReload = { viewModel.loadEvents() }
        )
    }
}

@Composable
fun EventsScreenContent(
    uiState: EventsUiState,
    onCategoryClick: (String) -> Unit,
    onEventClick: (EventMock) -> Unit,
    onFavoriteClick: (EventMock) -> Unit,
    onReload: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        if (uiState.errorMessage != null) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp).clickable { onReload() },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${uiState.errorMessage}. Нажми, чтобы обновить.", color = Color.Red)
            }
        }

        HeaderWithPatternAndFilters(
            categories = uiState.categories,
            selectedCategory = uiState.selectedCategory,
            onCategorySelected = onCategoryClick
        )

        Spacer(modifier = Modifier.height(5.dp))

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
                Text("Loading...", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(Color.White)
            ) {
                items(uiState.filteredEvents) { event ->
                    Box(modifier = Modifier.clickable { onEventClick(event) }) {
                        EventCard(
                            event = event,
                            onFavoriteClick = { onFavoriteClick(event) }
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                }

                if (uiState.filteredEvents.isEmpty() && !uiState.isLoading) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            Text("Событий не найдено", color = Color.Gray)
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
fun HeaderWithPatternAndFilters(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
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
                Text(
                    text = "События",
                    fontSize = 32.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )

//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.clickable { }) {
//                    Icon(
//                        imageVector = Icons.Outlined.Info,
//                        contentDescription = null,
//                        tint = ChelyabinskGreen,
//                        modifier = Modifier.size(24.dp)
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text("Сервисы", color = Color.Black, fontSize = 14.sp)
//                }
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
                    val backgroundColor = if (isSelected) ChelyabinskCardGreen else ChelyabinskCream

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

@Composable
fun EventCard(
    event: EventMock,
    onFavoriteClick: () -> Unit
) {
    var isFav by remember { mutableStateOf(event.isFavorite) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .size(width = 100.dp, height = 70.dp)
        ) {
            if (event.imageUrl.isNotBlank()) {
                AsyncImage(
                    model = event.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray)
                ) {
                    Text(
                        "???+???+??",
                        color = Color.White,
                        fontSize = 10.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = event.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = if (event.isFavorite) Icons.Default.Star else Icons.Outlined.Star,
                    contentDescription = null,
                    tint = if (event.isFavorite) Color(0xFFFFD700) else Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onFavoriteClick() }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            EventDetailItem(icon = Icons.Outlined.DateRange, text = formatIsoDateTimeRu(event.date))
            EventDetailItem(icon = Icons.Outlined.ShoppingCart, text = event.price)
            EventDetailItem(icon = Icons.Outlined.Place, text = event.location)
        }
    }
}

@Composable
fun EventDetailItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(bottom = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = ChelyabinskGreen,
            modifier = Modifier
                .size(16.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 10.sp,
            color = Color.Gray,
            lineHeight = 14.sp
        )
    }
}

@Preview
@Composable
fun EventsScreenPreview() {
    EventsScreenContent(
        uiState = EventsUiState(
            events = mockEvents,
            categories = listOf("Тест", "Тест 2")
        ),
        onCategoryClick = {},
        onEventClick = {},
        onFavoriteClick = {},
        onReload = {}
    )
}
