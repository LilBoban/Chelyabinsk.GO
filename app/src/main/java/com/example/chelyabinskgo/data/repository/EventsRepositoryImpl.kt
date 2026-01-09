package com.example.chelyabinskgo.data.repository

import com.example.chelyabinskgo.data.remote.ApiService
import com.example.chelyabinskgo.domain.model.EventMock

class EventsRepositoryImpl(
    private val apiService: ApiService
) : EventsRepository {
    override suspend fun getEvents(): List<EventMock> {
        val response = apiService.getEvents()
        if (!response.success) {
            throw IllegalStateException("Events request failed")
        }
        return response.data.map { dto ->
            EventMock(
                id = dto.id,
                title = dto.title,
                date = dto.date,
                price = "",
                location = dto.type,
                category = dto.type,
                imageUrl = "",
                isFavorite = false
            )
        }
    }
}
