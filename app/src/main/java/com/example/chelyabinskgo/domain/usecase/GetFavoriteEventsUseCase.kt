package com.example.chelyabinskgo.domain.usecase

import com.example.chelyabinskgo.data.repository.EventsRepository
import com.example.chelyabinskgo.domain.model.EventMock

class GetFavoriteEventsUseCase(
    private val repository: EventsRepository
) {
    suspend operator fun invoke(): List<EventMock> {
        return repository.getEvents().filter { it.isFavorite }
    }
}