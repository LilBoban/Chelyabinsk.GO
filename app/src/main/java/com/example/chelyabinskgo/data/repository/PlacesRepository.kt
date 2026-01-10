package com.example.chelyabinskgo.data.repository

import com.example.chelyabinskgo.domain.model.PlaceMock

interface PlacesRepository {
    suspend fun getPlaces(): List<PlaceMock>
    suspend fun toggleFavorite(place: PlaceMock)
    suspend fun isFavorite(placeId: Int): Boolean
}
