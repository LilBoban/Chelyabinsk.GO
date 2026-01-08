package com.example.chelyabinskgo.domain.model

data class PlaceMock(
    val id: Int,
    val title: String,
    val address: String,
    val category: String,
    val imageUrl: String = ""
)

val mockPlaces = listOf(
    PlaceMock(1, "Открытый каток в парке имени Терешковой", "г. Челябинск, ул. Рождественского, 6", "Куда пойти"),
    PlaceMock(2, "Пешеходная улица Кирова", "г. Челябинск, ул. Кирова", "Куда пойти"),

    PlaceMock(3, "Ресторан грузинской кухни \"Манана Мама\"", "г. Челябинск, ул. Тимирязева, 30", "Где поесть"),
    PlaceMock(4, "Гастробар \"D.O.M.\"", "г. Челябинск, ул. Кирова, 82", "Где поесть"),

    PlaceMock(5, "Отель Видгоф", "г. Челябинск, проспект Ленина, 26а", "Где разместиться"),
    PlaceMock(6, "Radisson Blu Hotel Chelyabinsk", "г. Челябинск, улица Труда, 179", "Где разместиться")
)