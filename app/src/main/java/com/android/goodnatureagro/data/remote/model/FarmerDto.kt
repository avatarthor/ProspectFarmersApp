package com.android.goodnatureagro.data.remote.model

data class FarmerDto(
    val id: Long? = null,
    val name: String,
    val phone_number: String,
    val location: String,
    val created_from: String = "mobile_app"
)