package com.example.myapplication.data.appdetails.local

import com.example.myapplication.data.dto.AppDto
import com.example.myapplication.domain.model.AppDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDetailsEntityMapper @Inject constructor() {

    fun toDomain(entity: AppDetailsEntity): AppDetails = AppDetails(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        category = entity.category,
        iconUrl = entity.iconUrl,
        isInWishlist = entity.isInWishlist
    )

    fun toEntity(dto: AppDto, isInWishlist: Boolean = false): AppDetailsEntity = AppDetailsEntity(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        category = dto.category,
        iconUrl = dto.iconUrl,
        isInWishlist = isInWishlist
    )
}
