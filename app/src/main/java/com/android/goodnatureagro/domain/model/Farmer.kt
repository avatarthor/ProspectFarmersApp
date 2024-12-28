package com.android.goodnatureagro.domain.model

data class Farmer(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val location: String,
    val isSynced: Boolean,
    val serverId: Long? = null,
    val createdAt: Long,
    val updatedAt: Long
)

fun Farmer.toEntity() = com.android.goodnatureagro.data.local.entity.FarmerEntity(
    id = id,
    name = name,
    phoneNumber = phoneNumber,
    location = location,
    isSynced = isSynced,
    serverId = serverId,
    createdAt = createdAt,
    updatedAt = updatedAt
)