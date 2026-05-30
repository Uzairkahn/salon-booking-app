package com.webera.salonbookingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.webera.salonbookingapp.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val user: FirebaseUser? = null,
    val errorMessage: String? = null
)

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _authState = MutableStateFlow(
        AuthUiState(
            isAuthenticated = authRepository.currentUser() != null,
            user = authRepository.currentUser()
        )
    )

    val authState: StateFlow<AuthUiState> = _authState.asStateFlow()

    fun signup(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val result = authRepository.signup(email, password)

            result.fold(
                onSuccess = { user ->
                    _authState.value = AuthUiState(
                        isLoading = false,
                        isAuthenticated = user != null,
                        user = user
                    )
                },
                onFailure = { exception ->
                    _authState.value = AuthUiState(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
            )
        }
    }

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val result = authRepository.login(email, password)

            result.fold(
                onSuccess = { user ->
                    _authState.value = AuthUiState(
                        isLoading = false,
                        isAuthenticated = user != null,
                        user = user
                    )
                },
                onFailure = { exception ->
                    _authState.value = AuthUiState(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
            )
        }
    }

    fun logout() {
        authRepository.logout()

        _authState.value = AuthUiState()
    }

    fun checkAuthenticationState() {
        val user = authRepository.currentUser()

        _authState.value = AuthUiState(
            isAuthenticated = user != null,
            user = user
        )
    }
}