package com.webera.salonbookingapp.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.webera.salonbookingapp.ui.components.CustomTextField
import com.webera.salonbookingapp.ui.components.PrimaryButton
import com.webera.salonbookingapp.ui.viewmodel.AuthViewModel

@Composable
fun SignupScreen(
    onLoginClick: () -> Unit,
    onAuthenticationSuccess: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var validationError by remember { mutableStateOf<String?>(null) }

    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState.isAuthenticated) {
        if (authState.isAuthenticated) {
            onAuthenticationSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomTextField(
            value = fullName,
            onValueChange = {
                fullName = it
                validationError = null
            },
            label = "Full Name"
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(
            value = email,
            onValueChange = {
                email = it
                validationError = null
            },
            label = "Email"
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                validationError = null
            },
            label = "Phone Number"
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(
            value = password,
            onValueChange = {
                password = it
                validationError = null
            },
            label = "Password"
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                validationError = null
            },
            label = "Confirm Password"
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            text = if (authState.isLoading) {
                "Creating Account..."
            } else {
                "Sign Up"
            },
            onClick = {

                validationError = when {
                    fullName.isBlank() ->
                        "Full Name is required."

                    email.isBlank() ->
                        "Email is required."

                    phoneNumber.isBlank() ->
                        "Phone Number is required."

                    password.isBlank() ->
                        "Password is required."

                    confirmPassword.isBlank() ->
                        "Confirm Password is required."

                    password != confirmPassword ->
                        "Passwords do not match."

                    else -> null
                }

                if (validationError != null) {
                    return@PrimaryButton
                }

                authViewModel.signup(
                    fullName = fullName.trim(),
                    email = email.trim(),
                    phoneNumber = phoneNumber.trim(),
                    password = password
                )
            }
        )

        if (authState.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        validationError?.let { error ->
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }

        authState.errorMessage?.let { error ->
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onLoginClick
        ) {
            Text(
                text = "Already have an account? Login"
            )
        }
    }
}