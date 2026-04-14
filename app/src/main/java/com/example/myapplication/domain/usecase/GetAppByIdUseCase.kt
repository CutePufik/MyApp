package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.repository.AppRepository
import javax.inject.Inject

class GetAppByIdUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(id: String): App? = appRepository.getAppById(id)
}
