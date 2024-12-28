package com.android.goodnatureagro.ui.farmers.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.goodnatureagro.domain.model.Farmer
import com.android.goodnatureagro.domain.usecase.DeleteFarmerUseCase
import com.android.goodnatureagro.domain.usecase.GetFarmersUseCase
import com.android.goodnatureagro.domain.usecase.SyncFarmersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmerListViewModel @Inject constructor(
    private val getFarmersUseCase: GetFarmersUseCase,
    private val deleteFarmerUseCase: DeleteFarmerUseCase,
    private val syncFarmersUseCase: SyncFarmersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FarmerListUiState())
    val uiState: StateFlow<FarmerListUiState> = _uiState.asStateFlow()

    init {
        loadFarmers()
    }

    fun loadFarmers() {
        viewModelScope.launch {
            getFarmersUseCase()
                .catch { error ->
                    _uiState.update {
                        it.copy(error = error.message ?: "Failed to load farmers")
                    }
                }
                .collect { farmers ->
                    _uiState.update {
                        it.copy(
                            farmers = farmers,
                            error = null
                        )
                    }
                }
        }
    }

    fun deleteFarmer(farmerId: String) {
        viewModelScope.launch {
            try {
                deleteFarmerUseCase(farmerId)
                _uiState.update { it.copy(error = null) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message ?: "Failed to delete farmer")
                }
            }
        }
    }

    fun syncFarmers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSyncing = true) }
            syncFarmersUseCase()
                .onSuccess {
                    _uiState.update {
                        it.copy(isSyncing = false, error = null)
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSyncing = false,
                            error = error.message ?: "Failed to sync farmers"
                        )
                    }
                }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class FarmerListUiState(
    val farmers: List<Farmer> = emptyList(),
    val isSyncing: Boolean = false,
    val error: String? = null
)