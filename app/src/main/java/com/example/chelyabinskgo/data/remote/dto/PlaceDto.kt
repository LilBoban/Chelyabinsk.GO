package com.example.chelyabinskgo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PlaceDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_ad")
    val updatedAd: String
)
