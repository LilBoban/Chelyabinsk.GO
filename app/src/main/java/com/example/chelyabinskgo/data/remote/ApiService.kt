package com.example.chelyabinskgo.data.remote

import com.example.chelyabinskgo.data.remote.dto.ApiResponse
import com.example.chelyabinskgo.data.remote.dto.EventDto
import com.example.chelyabinskgo.data.remote.dto.PlaceDto
import retrofit2.http.GET

interface ApiService {
    @GET("events/")
    suspend fun getEvents(): ApiResponse<List<EventDto>>

    @GET("places/")
    suspend fun getPlaces(): ApiResponse<List<PlaceDto>>
}
