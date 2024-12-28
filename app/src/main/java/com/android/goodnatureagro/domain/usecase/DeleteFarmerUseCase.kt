package com.android.goodnatureagro.domain.usecase

import com.android.goodnatureagro.data.repository.FarmerRepository
import javax.inject.Inject

class DeleteFarmerUseCase @Inject constructor(
    private val repository: FarmerRepository
) {
    suspend operator fun invoke(farmerId: String) {
        if (farmerId.isBlank()) {
            throw IllegalArgumentException("Farmer ID cannot be empty")
        }
        repository.deleteFarmer(farmerId)
    }
}