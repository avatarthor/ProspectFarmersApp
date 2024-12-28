package com.android.goodnatureagro.data.repository

import com.android.goodnatureagro.data.local.dao.FarmerDao
import com.android.goodnatureagro.data.local.entity.FarmerEntity
import com.android.goodnatureagro.data.remote.NetworkUtils
import com.android.goodnatureagro.data.remote.api.FarmerApiService
import com.android.goodnatureagro.data.remote.model.FarmerDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FarmerRepository @Inject constructor(
    private val farmerDao: FarmerDao,
    private val farmerApiService: FarmerApiService,
    private val networkUtils: NetworkUtils
) {
    fun getAllFarmers(): Flow<List<FarmerEntity>> {
        return farmerDao.getAllFarmers()
    }

    suspend fun addFarmer(name: String, phoneNumber: String, location: String) {
        val farmer = FarmerEntity(
            name = name,
            phoneNumber = phoneNumber,
            location = location
        )
        farmerDao.insertFarmer(farmer)

        if (networkUtils.isNetworkAvailable()) {
            try {
                val response = farmerApiService.createFarmer(
                    FarmerDto(
                        name = name,
                        phone_number = phoneNumber,
                        location = location
                    )
                )
                response.farmer?.id?.let { serverId ->
                    farmerDao.markFarmerAsSynced(farmer.id, serverId)
                }
            } catch (e: Exception) {
                // Failed to sync, will try again later
            }
        }
    }

    suspend fun updateFarmer(
        farmerId: String,
        name: String,
        phoneNumber: String,
        location: String
    ) {
        val existingFarmer = farmerDao.getFarmerById(farmerId) ?: return
        val updatedFarmer = existingFarmer.copy(
            name = name,
            phoneNumber = phoneNumber,
            location = location,
            isSynced = false,
            updatedAt = System.currentTimeMillis()
        )
        farmerDao.updateFarmer(updatedFarmer)

        if (networkUtils.isNetworkAvailable() && existingFarmer.serverId != null) {
            try {
                val response = farmerApiService.updateFarmer(
                    existingFarmer.serverId,
                    FarmerDto(
                        name = name,
                        phone_number = phoneNumber,
                        location = location
                    )
                )
                response.farmer?.let {
                    farmerDao.markFarmerAsSynced(farmerId, existingFarmer.serverId)
                }
            } catch (e: Exception) {
                // Failed to sync, will try again later
            }
        }
    }

    suspend fun deleteFarmer(farmerId: String) {
        val farmer = farmerDao.getFarmerById(farmerId) ?: return
        farmerDao.deleteFarmerById(farmerId)

        if (networkUtils.isNetworkAvailable() && farmer.serverId != null) {
            try {
                farmerApiService.deleteFarmer(farmer.serverId)
            } catch (e: Exception) {
                // Failed to sync deletion, but local delete succeeded
            }
        }
    }

    suspend fun syncUnsynkedFarmers() {
        if (!networkUtils.isNetworkAvailable()) return

        val unsynkedFarmers = farmerDao.getUnsyncedFarmers()
        for (farmer in unsynkedFarmers) {
            try {
                if (farmer.serverId == null) {
                    // Create new farmer on server
                    val response = farmerApiService.createFarmer(
                        FarmerDto(
                            name = farmer.name,
                            phone_number = farmer.phoneNumber,
                            location = farmer.location
                        )
                    )
                    response.farmer?.id?.let { serverId ->
                        farmerDao.markFarmerAsSynced(farmer.id, serverId)
                    }
                } else {
                    // Update existing farmer on server
                    val response = farmerApiService.updateFarmer(
                        farmer.serverId,
                        FarmerDto(
                            name = farmer.name,
                            phone_number = farmer.phoneNumber,
                            location = farmer.location
                        )
                    )
                    response.farmer?.let {
                        farmerDao.markFarmerAsSynced(farmer.id, farmer.serverId)
                    }
                }
            } catch (e: Exception) {
                // Failed to sync this farmer, will try again later
                continue
            }
        }
    }
}