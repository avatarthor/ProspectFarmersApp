package com.android.goodnatureagro.data.local.dao

import androidx.room.*
import com.android.goodnatureagro.data.local.entity.FarmerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FarmerDao {
    @Query("SELECT * FROM farmers ORDER BY createdAt DESC")
    fun getAllFarmers(): Flow<List<FarmerEntity>>

    @Query("SELECT * FROM farmers WHERE isSynced = 0")
    suspend fun getUnsyncedFarmers(): List<FarmerEntity>

    @Query("SELECT * FROM farmers WHERE id = :farmerId")
    suspend fun getFarmerById(farmerId: String): FarmerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarmer(farmer: FarmerEntity)

    @Update
    suspend fun updateFarmer(farmer: FarmerEntity)

    @Delete
    suspend fun deleteFarmer(farmer: FarmerEntity)

    @Query("UPDATE farmers SET isSynced = 1, serverId = :serverId WHERE id = :localId")
    suspend fun markFarmerAsSynced(localId: String, serverId: Long)

    @Query("DELETE FROM farmers WHERE id = :farmerId")
    suspend fun deleteFarmerById(farmerId: String)
}