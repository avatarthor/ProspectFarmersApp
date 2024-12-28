package com.android.goodnatureagro.ui.farmers.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.goodnatureagro.domain.usecase.AddFarmerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFarmerViewModel @Inject constructor(
    private val addFarmerUseCase: AddFarmerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddFarmerUiState())
    val uiState: StateFlow<AddFarmerUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun updateLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun addFarmer(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isSubmitting = true, error = null) }

                addFarmerUseCase(
                    name = uiState.value.name,
                    phoneNumber = uiState.value.phoneNumber,
                    location = uiState.value.location
                )

                onSuccess()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message ?: "Failed to add farmer")
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

data class AddFarmerUiState(
    val name: String = "",
    val phoneNumber: String = "",
    val location: String = "",
    val isSubmitting: Boolean = false,
    val error: String? = null
)