package com.example.chelyabinskgo.domain.usecase

import com.example.chelyabinskgo.data.repository.EventsRepository
import com.example.chelyabinskgo.domain.model.EventMock

class ToggleEventFavoriteUseCase(
    private val repository: EventsRepository
) {
    suspend operator fun invoke(event: EventMock) {
        repository.toggleFavorite(event)
    }
}