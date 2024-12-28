package com.android.goodnatureagro.ui.farmers.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.goodnatureagro.util.Constants.Validation

@Composable
fun FarmerForm(
    name: String,
    phoneNumber: String,
    location: String,
    onNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onSubmit: () -> Unit,
    isSubmitting: Boolean = false,
    submitButtonText: String = "Save",
    modifier: Modifier = Modifier
) {
    var nameError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var locationError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Name field
        OutlinedTextField(
            value = name,
            onValueChange = {
                onNameChange(it)
                nameError = validateName(it)
            },
            label = { Text("Farmer Name") },
            isError = nameError != null,
            supportingText = nameError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSubmitting
        )

        // Phone number field
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                onPhoneNumberChange(it)
                phoneError = validatePhoneNumber(it)
            },
            label = { Text("Phone Number") },
            isError = phoneError != null,
            supportingText = phoneError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSubmitting
        )

        // Location field
        OutlinedTextField(
            value = location,
            onValueChange = {
                onLocationChange(it)
                locationError = validateLocation(it)
            },
            label = { Text("Location") },
            isError = locationError != null,
            supportingText = locationError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSubmitting
        )

        // Submit button
        Button(
            onClick = {
                nameError = validateName(name)
                phoneError = validatePhoneNumber(phoneNumber)
                locationError = validateLocation(location)

                if (nameError == null && phoneError == null && locationError == null) {
                    onSubmit()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSubmitting && name.isNotBlank() && phoneNumber.isNotBlank() && location.isNotBlank()
        ) {
            if (isSubmitting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(submitButtonText)
            }
        }
    }
}

private fun validateName(name: String): String? {
    return when {
        name.isBlank() -> "Name is required"
        name.length < Validation.MIN_NAME_LENGTH -> "Name must be at least ${Validation.MIN_NAME_LENGTH} characters"
        name.length > Validation.MAX_NAME_LENGTH -> "Name must not exceed ${Validation.MAX_NAME_LENGTH} characters"
        else -> null
    }
}

private fun validatePhoneNumber(phone: String): String? {
    return when {
        phone.isBlank() -> "Phone number is required"
        phone.length < Validation.MIN_PHONE_LENGTH -> "Phone number must be at least ${Validation.MIN_PHONE_LENGTH} digits"
        phone.length > Validation.MAX_PHONE_LENGTH -> "Phone number must not exceed ${Validation.MAX_PHONE_LENGTH} digits"
        !phone.all { it.isDigit() } -> "Phone number must contain only digits"
        else -> null
    }
}

private fun validateLocation(location: String): String? {
    return when {
        location.isBlank() -> "Location is required"
        location.length > Validation.MAX_LOCATION_LENGTH -> "Location must not exceed ${Validation.MAX_LOCATION_LENGTH} characters"
        else -> null
    }
}