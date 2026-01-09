package com.example.chelyabinskgo.data.repository

import android.util.Log
import com.example.chelyabinskgo.data.remote.ApiService
import com.example.chelyabinskgo.domain.model.EventMock
import com.example.chelyabinskgo.domain.model.mockEvents

class EventsRepositoryImpl(
    private val apiService: ApiService
) : EventsRepository {
    override suspend fun getEvents(): List<EventMock> {
        return try {
            val response = apiService.getEvents()

            if (response.success) {
                response.data.map { dto ->
                    EventMock(
                        id = dto.id,
                        title = dto.title,
                        date = dto.date,
                        price = "от 0 руб.",
                        location = "Челябинск",
                        category = dto.type,
                        imageUrl = "",
                        isFavorite = false
                    )
                }
            } else {
                Log.e("EventsRepository", "Сервер не дал данных грузятся моки")
                mockEvents
            }
        } catch (e: Exception) {
            Log.e("EventsRepository", "Инет сломался ${e.message}, грузятся моки")
            mockEvents
        }
    }
}

