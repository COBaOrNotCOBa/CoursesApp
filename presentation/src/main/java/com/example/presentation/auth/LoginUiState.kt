package com.example.presentation.auth

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoginButtonEnabled: Boolean = false
)
