package com.example.chelyabinskgo.domain.model

data class EventMock(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val price: String,
    val location: String,
    val category: String,
    val imageUrl: String = "",
    var isFavorite: Boolean = false
)

val mockEvents = listOf(
    EventMock(
        1, "Выставка «Царь и патриарх»","блаблаблаблаблаблабла", "С 22 октября по 11 января", "от 150 руб.", "Исторический музей",
        category = "Выставки"
    ),
    EventMock(
        2, "Искусство XX века","блаблаблаблаблаблабла", "Ежедневно с 10:00", "300 руб.", "Картинная галерея",
        category = "Выставки"
    ),

    EventMock(
        3, "Балет «Щелкунчик»","блаблаблаблаблаблабла", "31 декабря, 18:00", "от 1500 руб.", "Театр Оперы и Балета",
        category = "Театры"
    ),
    EventMock(
        4, "Спектакль «Ревизор»","блаблаблаблаблаблабла","15 ноября, 19:00", "от 500 руб.", "Драмтеатр",
        category = "Театры"
    ),

    EventMock(
        5, "Обзорная по Челябинску","блаблаблаблаблаблабла","Каждые выходные", "бесплатно", "Площадь Революции",
        category = "Экскурсии"
    ),

    EventMock(
        6, "Открытие Ледового городка","блаблаблаблаблаблабла", "25 декабря", "бесплатно", "Площадь Революции",
        category = "Новый год"
    )
)