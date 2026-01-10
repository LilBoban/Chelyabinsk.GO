package com.example.chelyabinskgo.domain.usecase

import com.example.chelyabinskgo.data.repository.PlacesRepository
import com.example.chelyabinskgo.domain.model.PlaceMock

class TogglePlaceFavoriteUseCase(
    private val repository: PlacesRepository
) {
    suspend operator fun invoke(place: PlaceMock) {
        repository.toggleFavorite(place)
    }
}