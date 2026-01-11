package com.example.chelyabinskgo.data.mapper

object CategoryMapper {

    fun mapEventCategory(apiType: String): String {
        return when (apiType.trim().lowercase()) {
            "new year" -> "Новый год"
            "theaters" -> "Театры"
            "excursions" -> "Экскурсии"
            "exhibitions" -> "Выставки"
            "concerts" -> "Концерты"
            else -> apiType
        }
    }

    fun mapPlaceCategory(apiType: String): String {
        return when (apiType.trim().lowercase()) {
            "where to eat" -> "Где поесть"
            "what to do" -> "Куда пойти"
            "where to live" -> "Где разместиться"
            "where to go" -> "Куда пойти"
            "services" -> "Сервисы"
            else -> apiType
        }
    }
}