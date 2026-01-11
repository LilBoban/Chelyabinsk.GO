package com.example.chelyabinskgo.data.repository

import android.util.Log
import com.example.chelyabinskgo.data.database.dao.FavoritesDao
import com.example.chelyabinskgo.data.database.entity.FavoritePlaceEntity
import com.example.chelyabinskgo.data.remote.ApiService
import com.example.chelyabinskgo.domain.model.PlaceMock
import com.example.chelyabinskgo.domain.model.mockPlaces

class PlacesRepositoryImpl(
    private val apiService: ApiService,
    private val favoritesDao: FavoritesDao
) : PlacesRepository {
    private fun normalizeImageUrl(url: String?): String {
        if (url.isNullOrBlank()) return ""
        return url.replace("http://localhost:8000", "http://10.0.2.2:8000")
    }

    override suspend fun getPlaces(): List<PlaceMock> {
        val favoriteIds = favoritesDao.getFavoritePlaceIds()

        return try {
            val response = apiService.getPlaces()

            if (response.success) {
                response.data.map { dto ->
                    PlaceMock(
                        id = dto.id,
                        title = dto.title,
                        description = dto.description,
                        address = "Челябинск",
                        category = dto.type,
                        imageUrl = normalizeImageUrl(dto.imageUrl),
                        isFavorite = favoriteIds.contains(dto.id)
                    )
                }
            } else {
                Log.e("PlacesRepository", "Server success=false, loading mocks")
                mockPlaces
            }
        } catch (e: Exception) {
            Log.e("PlacesRepository", "Network error: ${e.message}, loading mocks")
            mockPlaces
        }
    }
    override suspend fun toggleFavorite(place: PlaceMock) {
        if (place.isFavorite) {
            favoritesDao.removePlaceFromFavorites(place.id)
        } else {
            favoritesDao.addPlaceToFavorites(FavoritePlaceEntity(place.id))
        }
    }

    override suspend fun isFavorite(placeId: Int): Boolean {
        return favoritesDao.getFavoritePlaceIds().contains(placeId)
    }
}