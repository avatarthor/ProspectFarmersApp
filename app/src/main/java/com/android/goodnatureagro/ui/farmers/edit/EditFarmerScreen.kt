package com.android.goodnatureagro.ui.farmers.edit

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.goodnatureagro.R
import com.android.goodnatureagro.ui.farmers.components.FarmerForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFarmerScreen(
    onNavigateBack: () -> Unit,
    viewModel: EditFarmerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(
                message = it,
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Short
            )
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Farmer") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Navigate back"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        FarmerForm(
            name = uiState.name,
            phoneNumber = uiState.phoneNumber,
            location = uiState.location,
            onNameChange = viewModel::updateName,
            onPhoneNumberChange = viewModel::updatePhoneNumber,
            onLocationChange = viewModel::updateLocation,
            onSubmit = {
                viewModel.updateFarmer(onSuccess = onNavigateBack)
            },
            isSubmitting = uiState.isSubmitting,
            submitButtonText = "Update Farmer",
            modifier = Modifier.padding(paddingValues)
        )
    }
}