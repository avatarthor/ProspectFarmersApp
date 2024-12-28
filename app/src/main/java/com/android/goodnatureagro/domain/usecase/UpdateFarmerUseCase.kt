package com.android.goodnatureagro.domain.usecase

import com.android.goodnatureagro.data.repository.FarmerRepository
import javax.inject.Inject

class UpdateFarmerUseCase @Inject constructor(
    private val repository: FarmerRepository
) {
    suspend operator fun invoke(
        farmerId: String,
        name: String,
        phoneNumber: String,
        location: String
    ) {
        if (farmerId.isBlank()) {
            throw IllegalArgumentException("Farmer ID cannot be empty")
        }
        if (name.isBlank()) {
            throw IllegalArgumentException("Name cannot be empty")
        }
        if (phoneNumber.isBlank()) {
            throw IllegalArgumentException("Phone number cannot be empty")
        }
        if (location.isBlank()) {
            throw IllegalArgumentException("Location cannot be empty")
        }

        repository.updateFarmer(
            farmerId = farmerId,
            name = name.trim(),
            phoneNumber = phoneNumber.trim(),
            location = location.trim()
        )
    }
}