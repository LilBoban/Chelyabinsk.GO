package com.example.chelyabinskgo.data.repository

import com.example.chelyabinskgo.domain.model.EventMock

interface EventsRepository {
    suspend fun getEvents(): List<EventMock>
}
