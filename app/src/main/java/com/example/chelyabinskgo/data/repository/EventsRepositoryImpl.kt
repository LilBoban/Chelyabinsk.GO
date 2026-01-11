package com.example.chelyabinskgo.data.repository

import android.util.Log
import com.example.chelyabinskgo.data.database.dao.FavoritesDao
import com.example.chelyabinskgo.data.database.entity.FavoriteEventEntity
import com.example.chelyabinskgo.data.remote.ApiService
import com.example.chelyabinskgo.domain.model.EventMock
import com.example.chelyabinskgo.domain.model.mockEvents

class EventsRepositoryImpl(
    private val apiService: ApiService,
    private val favoritesDao: FavoritesDao
) : EventsRepository {
    private fun normalizeImageUrl(url: String?): String {
        if (url.isNullOrBlank()) return ""
        return url.replace("http://localhost:8000", "http://10.0.2.2:8000")
    }

    override suspend fun getEvents(): List<EventMock> {
        val favoriteIds = favoritesDao.getFavoriteEventIds()

        val eventsFromApi = try {
            val response = apiService.getEvents()

            if (response.success) {
                response.data.map { dto ->
                    EventMock(
                        id = dto.id,
                        title = dto.title,
                        description = dto.description,
                        date = dto.date,
                        price = dto.price,
                        location = dto.location,
                        category = dto.type,
                        imageUrl = normalizeImageUrl(dto.imageUrl),
                        isFavorite = favoriteIds.contains(dto.id)
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

        if (eventsFromApi === mockEvents) {
            return eventsFromApi.map { it.copy(isFavorite = favoriteIds.contains(it.id)) }
        }

        return eventsFromApi
    }

    override suspend fun toggleFavorite(event: EventMock) {
        if (event.isFavorite) {
            favoritesDao.removeEventFromFavorites(event.id)
        } else {
            favoritesDao.addEventToFavorites(FavoriteEventEntity(event.id))
        }
    }
    override suspend fun isFavorite(eventId: Int): Boolean {
        return favoritesDao.getFavoriteEventIds().contains(eventId)
    }

}

