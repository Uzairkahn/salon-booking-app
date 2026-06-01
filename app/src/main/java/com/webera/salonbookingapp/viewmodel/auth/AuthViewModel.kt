package com.webera.salonbookingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.webera.salonbookingapp.data.model.User
import com.webera.salonbookingapp.data.repository.auth.AuthRepository
import com.webera.salonbookingapp.data.repository.user.UserRepository
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
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _authState = MutableStateFlow(
        AuthUiState(
            isAuthenticated = authRepository.currentUser() != null,
            user = authRepository.currentUser()
        )
    )

    val authState: StateFlow<AuthUiState> = _authState.asStateFlow()

    fun signup(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String
    ) {
        viewModelScope.launch {

            _authState.value = _authState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val authResult = authRepository.signup(
                email = email,
                password = password
            )

            authResult.fold(
                onSuccess = { firebaseUser ->

                    val uid = firebaseUser?.uid

                    if (uid.isNullOrBlank()) {
                        _authState.value = AuthUiState(
                            isLoading = false,
                            errorMessage = "Failed to obtain user ID."
                        )
                        return@fold
                    }

                    val user = User(
                        uid = uid,
                        fullName = fullName,
                        email = email,
                        phoneNumber = phoneNumber,
                        role = "user",
                        createdAt = Timestamp.now()
                    )

                    val saveResult = userRepository.saveUser(user)

                    saveResult.fold(
                        onSuccess = {
                            _authState.value = AuthUiState(
                                isLoading = false,
                                isAuthenticated = true,
                                user = firebaseUser
                            )
                        },
                        onFailure = { exception ->
                            _authState.value = AuthUiState(
                                isLoading = false,
                                errorMessage = exception.message
                            )
                        }
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

            val result = authRepository.login(
                email = email,
                password = password
            )

            result.fold(
                onSuccess = { firebaseUser ->
                    _authState.value = AuthUiState(
                        isLoading = false,
                        isAuthenticated = firebaseUser != null,
                        user = firebaseUser
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