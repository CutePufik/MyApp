package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.AppDetailsRepository
import javax.inject.Inject

class ToggleWishlistUseCase @Inject constructor(
    private val appDetailsRepository: AppDetailsRepository
) {
    suspend operator fun invoke(id: String) {
        appDetailsRepository.toggleWishlist(id)
    }
}
