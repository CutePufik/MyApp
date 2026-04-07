package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.AppDetails
import com.example.myapplication.domain.repository.AppDetailsRepository
import javax.inject.Inject

class GetAppDetailsUseCase @Inject constructor(
    private val appDetailsRepository: AppDetailsRepository
) {
    suspend operator fun invoke(id: String): AppDetails = appDetailsRepository.getAppDetails(id)
}
