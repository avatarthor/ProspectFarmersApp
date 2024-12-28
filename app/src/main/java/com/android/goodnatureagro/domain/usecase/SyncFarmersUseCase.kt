package com.android.goodnatureagro.domain.usecase

import com.android.goodnatureagro.data.remote.NetworkUtils
import com.android.goodnatureagro.data.repository.FarmerRepository
import javax.inject.Inject

class SyncFarmersUseCase @Inject constructor(
    private val repository: FarmerRepository,
    private val networkUtils: NetworkUtils
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            if (!networkUtils.isNetworkAvailable()) {
                return Result.failure(Exception("No internet connection"))
            }
            repository.syncUnsynkedFarmers()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}