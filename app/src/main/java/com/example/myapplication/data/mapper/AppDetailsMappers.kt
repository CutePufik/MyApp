package com.example.myapplication.data.mapper

import com.example.myapplication.data.local.entity.AppDetailsEntity
import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.model.AppDetails

fun AppDetailsEntity.toDomain(): AppDetails = AppDetails(
    id = id,
    name = name,
    description = description,
    category = category,
    iconUrl = iconUrl,
    isInWishlist = isInWishlist
)

fun AppDetails.toEntity(): AppDetailsEntity = AppDetailsEntity(
    id = id,
    name = name,
    description = description,
    category = category,
    iconUrl = iconUrl,
    isInWishlist = isInWishlist
)

fun App.toAppDetailsEntity(isInWishlist: Boolean): AppDetailsEntity = AppDetailsEntity(
    id = id,
    name = name,
    description = description,
    category = category,
    iconUrl = iconUrl,
    isInWishlist = isInWishlist
)
