package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.repository.AppRepository
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(): List<App> = appRepository.getApps()
}
