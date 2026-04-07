package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.AppDetails
import com.example.myapplication.domain.repository.AppDetailsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveAppDetailsUseCase @Inject constructor(
    private val appDetailsRepository: AppDetailsRepository
) {
    operator fun invoke(id: String): Flow<AppDetails> = appDetailsRepository.observeAppDetails(id)
}
