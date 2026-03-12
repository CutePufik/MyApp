package com.example.myapplication.data

import com.example.myapplication.R

object AppRepository {
    val apps = listOf(
        AppItem(
            id = 1,
            name = "СберБанк Онлайн — с Салютом",
            description = "Больше чем банк",
            category = "Финансы",
            iconRes = R.drawable.ic_sber
        ),
        AppItem(
            id = 2,
            name = "Яндекс.Браузер — с Алисой",
            description = "Быстрый и безопасный браузер",
            category = "Инструменты",
            iconRes = R.drawable.ic_yandex_browser
        ),
        AppItem(
            id = 3,
            name = "Почта Mail.ru",
            description = "Почтовый клиент для любых ящиков",
            category = "Инструменты",
            iconRes = R.drawable.ic_mail
        ),
        AppItem(
            id = 4,
            name = "Яндекс Навигатор",
            description = "Парковки и заправки — по пути",
            category = "Транспорт",
            iconRes = R.drawable.ic_navigator
        ),
        AppItem(
            id = 5,
            name = "Мой МТС",
            description = "Мой МТС — центр экосистемы МТС",
            category = "Инструменты",
            iconRes = R.drawable.ic_mts
        ),
        AppItem(
            id = 6,
            name = "Яндекс — с Алисой",
            description = "Яндекс — поиск всегда под рукой",
            category = "Инструменты",
            iconRes = R.drawable.ic_yandex
        )
    )

    fun getAppById(id: Int): AppItem? = apps.find { it.id == id }
}