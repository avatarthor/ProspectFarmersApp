package com.android.goodnatureagro.domain.usecase

import com.android.goodnatureagro.data.repository.FarmerRepository
import javax.inject.Inject

class AddFarmerUseCase @Inject constructor(
    private val repository: FarmerRepository
) {
    suspend operator fun invoke(
        name: String,
        phoneNumber: String,
        location: String
    ) {
        if (name.isBlank()) {
            throw IllegalArgumentException("Name cannot be empty")
        }
        if (phoneNumber.isBlank()) {
            throw IllegalArgumentException("Phone number cannot be empty")
        }
        if (location.isBlank()) {
            throw IllegalArgumentException("Location cannot be empty")
        }

        repository.addFarmer(
            name = name.trim(),
            phoneNumber = phoneNumber.trim(),
            location = location.trim()
        )
    }
}