package com.example.myapplication.data.network

import com.example.myapplication.data.dto.AppDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApi {
    @GET("catalog")
    suspend fun getCatalog(): List<AppDto>

    @GET("catalog/{id}")
    suspend fun getAppById(@Path("id") id: String): AppDto
}
