package com.example.myapplication.domain.model

data class AppDetails(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String,
    val isInWishlist: Boolean = false
)
