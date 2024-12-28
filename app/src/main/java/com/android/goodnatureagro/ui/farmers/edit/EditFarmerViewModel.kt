package com.android.goodnatureagro.ui.farmers.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.goodnatureagro.domain.usecase.GetFarmersUseCase
import com.android.goodnatureagro.domain.usecase.UpdateFarmerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditFarmerViewModel @Inject constructor(
    private val updateFarmerUseCase: UpdateFarmerUseCase,
    private val getFarmersUseCase: GetFarmersUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val farmerId: String = checkNotNull(savedStateHandle["farmerId"])

    private val _uiState = MutableStateFlow(EditFarmerUiState())
    val uiState: StateFlow<EditFarmerUiState> = _uiState.asStateFlow()

    init {
        loadFarmer()
    }

    private fun loadFarmer() {
        viewModelScope.launch {
            getFarmersUseCase()
                .map { farmers -> farmers.find { it.id == farmerId } }
                .collect { farmer ->
                    farmer?.let {
                        _uiState.update { state ->
                            state.copy(
                                name = farmer.name,
                                phoneNumber = farmer.phoneNumber,
                                location = farmer.location,
                                error = null
                            )
                        }
                    } ?: run {
                        _uiState.update {
                            it.copy(error = "Farmer not found")
                        }
                    }
                }
        }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun updateLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun updateFarmer(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isSubmitting = true, error = null) }

                updateFarmerUseCase(
                    farmerId = farmerId,
                    name = uiState.value.name,
                    phoneNumber = uiState.value.phoneNumber,
                    location = uiState.value.location
                )

                onSuccess()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message ?: "Failed to update farmer")
                }
            } finally {
                _uiState.update { it.copy(isSubmitting = false) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class EditFarmerUiState(
    val name: String = "",
    val phoneNumber: String = "",
    val location: String = "",
    val isSubmitting: Boolean = false,
    val error: String? = null
)