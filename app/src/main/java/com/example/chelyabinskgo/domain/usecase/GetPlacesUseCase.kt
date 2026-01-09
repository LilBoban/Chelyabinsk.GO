package com.example.chelyabinskgo.domain.usecase

import com.example.chelyabinskgo.data.repository.PlacesRepository
import com.example.chelyabinskgo.domain.model.PlaceMock

class GetPlacesUseCase(
    private val repository: PlacesRepository
) {
    suspend operator fun invoke(): List<PlaceMock> = repository.getPlaces()
}
