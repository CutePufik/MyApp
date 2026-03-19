package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.AppDto
import com.example.myapplication.domain.model.App

fun AppDto.toDomain(): App = App(
    id = id,
    name = name,
    description = description,
    category = category,
    iconRes = iconRes
)

fun List<AppDto>.toDomainList(): List<App> = map { it.toDomain() }

