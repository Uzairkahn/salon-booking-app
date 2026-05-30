package com.webera.salonbookingapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.webera.salonbookingapp.ui.components.PrimaryButton
import com.webera.salonbookingapp.ui.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    onLogoutClick: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.headlineMedium
        )

        PrimaryButton(
            text = "Logout",
            onClick = {
                authViewModel.logout()
                onLogoutClick()
            }
        )
    }
}