package com.android.goodnatureagro.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.goodnatureagro.data.local.dao.FarmerDao
import com.android.goodnatureagro.data.local.entity.FarmerEntity

@Database(
    entities = [FarmerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun farmerDao(): FarmerDao

    companion object {
        const val DATABASE_NAME = "goodnature_agro_db"
    }
}