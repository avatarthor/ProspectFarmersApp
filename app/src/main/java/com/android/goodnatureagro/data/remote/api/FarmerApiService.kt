package com.android.goodnatureagro.data.remote.api

import com.android.goodnatureagro.data.remote.model.ApiResponse
import com.android.goodnatureagro.data.remote.model.FarmerDto
import retrofit2.http.*

interface FarmerApiService {
    @GET("farmers/get-all")
    suspend fun getAllFarmers(): List<FarmerDto>

    @POST("farmers/add-new")
    suspend fun createFarmer(@Body farmer: FarmerDto): ApiResponse<FarmerDto>

    @PUT("farmers/{id}")
    suspend fun updateFarmer(
        @Path("id") id: Long,
        @Body farmer: FarmerDto
    ): ApiResponse<FarmerDto>

    @DELETE("farmers/{id}")
    suspend fun deleteFarmer(@Path("id") id: Long): ApiResponse<Unit>
}