package com.android.goodnatureagro.domain.usecase

import com.android.goodnatureagro.data.local.entity.FarmerEntity
import com.android.goodnatureagro.data.repository.FarmerRepository
import com.android.goodnatureagro.domain.model.Farmer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFarmersUseCase @Inject constructor(
    private val repository: FarmerRepository
) {
    operator fun invoke(): Flow<List<Farmer>> {
        return repository.getAllFarmers().map { farmers ->
            farmers.map { it.toDomainModel() }
        }
    }

    private fun FarmerEntity.toDomainModel(): Farmer {
        return Farmer(
            id = id,
            name = name,
            phoneNumber = phoneNumber,
            location = location,
            isSynced = isSynced,
            serverId = serverId,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}