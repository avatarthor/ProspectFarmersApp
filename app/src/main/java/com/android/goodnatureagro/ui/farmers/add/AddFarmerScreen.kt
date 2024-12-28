package com.android.goodnatureagro.ui.farmers.add

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.goodnatureagro.R
import com.android.goodnatureagro.ui.farmers.components.FarmerForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFarmerScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddFarmerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Handle error messages
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Short
            )
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Farmer") },
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
                viewModel.addFarmer(onSuccess = onNavigateBack)
            },
            isSubmitting = uiState.isSubmitting,
            submitButtonText = "Add Farmer",
            modifier = Modifier.padding(paddingValues)
        )
    }
}