package com.example.myapplication.presentation

import com.example.myapplication.data.repository.AppRepositoryImpl
import com.example.myapplication.domain.repository.AppRepository

object AppRepositoryProvider {
    fun provide(): AppRepository = AppRepositoryImpl()
}

