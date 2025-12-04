package com.example.domain.usecase

class ValidateEmailUseCase {

    private val emailRegex =
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    private val cyrillicRegex = "[А-Яа-яЁё]".toRegex()

    operator fun invoke(email: String): Boolean {
        if (email.isBlank()) return false
        if (cyrillicRegex.containsMatchIn(email)) return false

        return emailRegex.matches(email)
    }
}
