package com.example.chelyabinskgo.presentation.screens.places

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.chelyabinskgo.R
import com.example.chelyabinskgo.domain.model.PlaceMock
import com.example.chelyabinskgo.presentation.viewmodel.PlaceDetailsViewModel
import com.example.chelyabinskgo.ui.theme.ChelyabinskCardGreen
import com.example.chelyabinskgo.ui.theme.ChelyabinskGray
import org.koin.androidx.compose.koinViewModel

data class PlaceDetailsScreen(val place: PlaceMock) : Screen {

    @Composable
    override fun Content() {
        val viewModel: PlaceDetailsViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        LaunchedEffect(Unit) { viewModel.init(place) }
        val isFavorite by viewModel.isFavorite.collectAsState()

        PlaceDetailsContent(
            place = place,
            isFavorite = isFavorite,
            onBackClick = { navigator.pop() },
            onFavoriteClick = { viewModel.toggleFavorite(place) },
            onShareClick = { Toast.makeText(context, "TODO: Поделиться", Toast.LENGTH_SHORT).show() },
            onBookClick = { Toast.makeText(context, "Бронь...", Toast.LENGTH_SHORT).show() }
        )
    }
}

@Composable
fun PlaceDetailsContent(
    place: PlaceMock,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit,
    onBookClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (place.imageUrl.isNotBlank()) {
            AsyncImage(
                model = place.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.White)
                    .statusBarsPadding()
                    .padding(top = 46.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.splash_background_pattern),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(ChelyabinskGray)
                    .statusBarsPadding()
                    .padding(top = 46.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .border(1.dp,Color.LightGray, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(285.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                Text(
                    text = place.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                PlaceInfoText(text = place.address)


                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onBookClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ChelyabinskCardGreen,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Забронировать столик", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Описание:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = place.description.ifEmpty { "Описание отсутствует..." },
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color.Black.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад",
                tint = Color.DarkGray,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onBackClick() }
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onShareClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = Color.DarkGray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Поделиться", color = Color.DarkGray, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    imageVector = if (isFavorite) Icons.Default.Star else Icons.Outlined.Star,
                    contentDescription = "В избранное",
                    tint = if (isFavorite) Color(0xFFFFD700) else Color.DarkGray,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onFavoriteClick() }
                )
            }
        }
    }
}

@Composable
fun PlaceInfoText(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Preview
@Composable
fun PlaceDetailsScreenPreview() {
    PlaceDetailsContent(
        place = PlaceMock(1, "Ресторан", "Вкусно", "Ленина 1", "Еда", isFavorite = true),
        isFavorite = true,
        onBackClick = {},
        onFavoriteClick = {},
        onShareClick = {},
        onBookClick = {}
    )
}