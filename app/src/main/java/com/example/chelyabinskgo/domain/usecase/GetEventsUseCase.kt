package com.example.chelyabinskgo.domain.usecase

import com.example.chelyabinskgo.data.repository.EventsRepository
import com.example.chelyabinskgo.domain.model.EventMock

class GetEventsUseCase(
    private val repository: EventsRepository
) {
    suspend operator fun invoke(): List<EventMock> = repository.getEvents()
}
