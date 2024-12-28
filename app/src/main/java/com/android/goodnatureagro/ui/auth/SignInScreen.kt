package com.android.goodnatureagro.ui.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.goodnatureagro.R
import com.android.goodnatureagro.util.Constants

import androidx.compose.ui.graphics.Color

@Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf<String?>(null) }

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            result.data?.let { intent ->
                viewModel.handleGoogleSignInResult(intent)
            }
        }
    )

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                onSignInSuccess()
            }
            is AuthState.Error -> {
                showError = (authState as AuthState.Error).message
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // App Logo
                val isDarkTheme = isSystemInDarkTheme()
                Image(
                    painter = painterResource(
                        id = if (isDarkTheme) R.drawable.logo_light else R.drawable.logo
                    ),
                    contentDescription = "Good Nature Agro Logo",
                    modifier = Modifier
                        .size(170.dp)
                        .padding(bottom = 16.dp)
                )

                // Title
                Text(
                    text = "Farmer Management System",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Login into your account",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        showError = null  // Clear error when user types
                    },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        showError = null  // Clear error when user types
                    },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                // Error Message
                showError?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Sign In Button
                Button(
                    onClick = {
                        when {
                            email.isEmpty() && password.isEmpty() -> {
                                showError = "Please enter your email and password"
                            }
                            email.isEmpty() -> {
                                showError = "Please enter your email"
                            }
                            password.isEmpty() -> {
                                showError = "Please enter your password"
                            }
                            else -> {
                                showError = null
                                viewModel.signInWithEmail(email, password)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Sign In",
                        color = Color.White
                    )
                }

                // Or Divider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(modifier = Modifier.weight(1f))
                    Text(
                        text = "or",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Divider(modifier = Modifier.weight(1f))
                }

                // Google Sign In Button
                OutlinedButton(
                    onClick = {
                        googleSignInLauncher.launch(viewModel.getGoogleSignInIntent())
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sign in with Google")
                }
            }
        }
    }
}