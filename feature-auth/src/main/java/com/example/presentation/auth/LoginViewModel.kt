package com.example.presentation.auth

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.AuthUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        _uiState.update { currentState ->
            val updatedState = currentState.copy(email = email)
            updatedState.copy(
                isLoginButtonEnabled = isLoginButtonEnabled(
                    email = updatedState.email,
                    password = updatedState.password
                )
            )
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { currentState ->
            val updatedState = currentState.copy(password = password)
            updatedState.copy(
                isLoginButtonEnabled = isLoginButtonEnabled(
                    email = updatedState.email,
                    password = updatedState.password
                )
            )
        }
    }

    private fun isLoginButtonEnabled(email: String, password: String): Boolean {
        val isEmailValid = authUseCases.validateEmail(email)
        val isPasswordNotBlank = password.isNotBlank()
        return isEmailValid && isPasswordNotBlank
    }
}
