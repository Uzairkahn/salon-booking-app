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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.collectAsState
import com.webera.salonbookingapp.ui.components.CustomTextField
import com.webera.salonbookingapp.ui.components.PrimaryButton
import com.webera.salonbookingapp.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onSignupClick: () -> Unit,
    onAuthenticationSuccess: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password"
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            text = if (authState.isLoading) "Logging In..." else "Login",
            onClick = {
                authViewModel.login(
                    email = email.trim(),
                    password = password
                )
            }
        )

        if (authState.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        authState.errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onSignupClick
        ) {
            Text(text = "Don't have an account? Sign Up")
        }
    }
}