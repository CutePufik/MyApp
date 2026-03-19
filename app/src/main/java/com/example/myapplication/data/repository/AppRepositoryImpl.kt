package com.example.myapplication.data.repository

import com.example.myapplication.R
import com.example.myapplication.data.dto.AppDto
import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.mapper.toDomainList
import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.repository.AppRepository

class AppRepositoryImpl : AppRepository {


    private val appsDto = listOf(
        AppDto(
            id = 1,
            name = "СберБанк Онлайн — с Салютом",
            description = "Больше чем банк",
            category = "Финансы",
            iconRes = R.drawable.ic_sber
        ),
        AppDto(
            id = 2,
            name = "Яндекс.Браузер — с Алисой",
            description = "Быстрый и безопасный браузер",
            category = "Инструменты",
            iconRes = R.drawable.ic_yandex_browser
        ),
        AppDto(
            id = 3,
            name = "Почта Mail.ru",
            description = "Почтовый клиент для любых ящиков",
            category = "Инструменты",
            iconRes = R.drawable.ic_mail
        ),
        AppDto(
            id = 4,
            name = "Яндекс Навигатор",
            description = "Парковки и заправки — по пути",
            category = "Транспорт",
            iconRes = R.drawable.ic_navigator
        ),
        AppDto(
            id = 5,
            name = "Мой МТС",
            description = "Мой МТС — центр экосистемы МТС",
            category = "Инструменты",
            iconRes = R.drawable.ic_mts
        ),
        AppDto(
            id = 6,
            name = "Яндекс — с Алисой",
            description = "Яндекс — поиск всегда под рукой",
            category = "Инструменты",
            iconRes = R.drawable.ic_yandex
        )
    )

    override fun getApps(): List<App> = appsDto.toDomainList()

    override fun getAppById(id: Int): App? = appsDto.find { it.id == id }?.toDomain()
}

