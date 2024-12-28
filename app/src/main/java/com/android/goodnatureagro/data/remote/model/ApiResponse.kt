package com.android.goodnatureagro.data.remote.model

data class ApiResponse<T>(
    val message: String,
    val farmer: T? = null
)