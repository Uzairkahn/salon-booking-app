package com.webera.salonbookingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.webera.salonbookingapp.data.repository.AuthRepository

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel()