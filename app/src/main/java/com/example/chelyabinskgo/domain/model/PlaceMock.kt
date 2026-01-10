package com.example.chelyabinskgo.domain.model

data class PlaceMock(
    val id: Int,
    val title: String,
    val description: String,
    val address: String,
    val category: String,
    val imageUrl: String = "",
    var isFavorite: Boolean = false
)

val mockPlaces = listOf(
    PlaceMock(1, "Открытый каток в парке имени Терешковой", "блаблаблаблаблаблабла", "г. Челябинск, ул. Рождественского, 6", "Куда пойти"),
    PlaceMock(2, "Пешеходная улица Кирова","блаблаблаблаблаблабла",  "г. Челябинск, ул. Кирова", "Куда пойти"),

    PlaceMock(3, "Ресторан грузинской кухни \"Манана Мама\"","блаблаблаблаблаблабла",  "г. Челябинск, ул. Тимирязева, 30", "Где поесть"),
    PlaceMock(4, "Гастробар \"D.O.M.\"","блаблаблаблаблаблабла",  "г. Челябинск, ул. Кирова, 82", "Где поесть"),

    PlaceMock(5, "Отель Видгоф","блаблаблаблаблаблабла", "г. Челябинск, проспект Ленина, 26а", "Где разместиться"),
    PlaceMock(6, "Radisson Blu Hotel Chelyabinsk","блаблаблаблаблаблабла",  "г. Челябинск, улица Труда, 179", "Где разместиться")
)