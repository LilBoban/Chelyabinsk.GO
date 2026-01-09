package com.example.chelyabinskgo.data.repository

import com.example.chelyabinskgo.data.remote.ApiService
import com.example.chelyabinskgo.domain.model.PlaceMock

class PlacesRepositoryImpl(
    private val apiService: ApiService
) : PlacesRepository {
    override suspend fun getPlaces(): List<PlaceMock> {
        val response = apiService.getPlaces()
        if (!response.success) {
            throw IllegalStateException("Places request failed")
        }
        return response.data.map { dto ->
            PlaceMock(
                id = dto.id,
                title = dto.title,
                address = dto.description,
                category = dto.type,
                imageUrl = ""
            )
        }
    }
}
