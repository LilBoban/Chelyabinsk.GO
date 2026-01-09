package com.example.chelyabinskgo.data.repository

import android.util.Log
import com.example.chelyabinskgo.data.remote.ApiService
import com.example.chelyabinskgo.domain.model.PlaceMock
import com.example.chelyabinskgo.domain.model.mockPlaces

class PlacesRepositoryImpl(
    private val apiService: ApiService
) : PlacesRepository {
    override suspend fun getPlaces(): List<PlaceMock> {
        return try {
            val response = apiService.getPlaces()

            if (response.success) {
                response.data.map { dto ->
                    PlaceMock(
                        id = dto.id,
                        title = dto.title,
                        address = dto.description,
                        category = dto.type,
                        imageUrl = ""
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
}
