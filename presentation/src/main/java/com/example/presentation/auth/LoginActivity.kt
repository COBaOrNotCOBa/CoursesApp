package com.example.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.presentation.databinding.ActivityLoginBinding
import com.example.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    private val nonCyrillicInputFilter = InputFilter { source, start, end, _, _, _ ->
        val filtered = buildString {
            for (index in start until end) {
                val character = source[index]
                if (!character.isCyrillic()) {
                    append(character)
                }
            }
        }

        if (filtered.length == end - start) {
            null
        } else {
            filtered
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInputFilters()
        observeViewModel()
        setupListeners()
    }

    private fun setupInputFilters() {
        val emailEditText = binding.editTextEmail
        val existingFilters = emailEditText.filters
        emailEditText.filters = existingFilters + nonCyrillicInputFilter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    binding.buttonLogin.isEnabled = state.isLoginButtonEnabled
                }
            }
        }
    }

    private fun setupListeners() {
        binding.editTextEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text?.toString().orEmpty())
        }

        binding.editTextPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChanged(text?.toString().orEmpty())
        }

        binding.buttonLogin.setOnClickListener {
            if (binding.buttonLogin.isEnabled) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.buttonVk.setOnClickListener {
            openUrlInBrowser(AuthExternalLinks.VK_URL)
        }

        binding.buttonOk.setOnClickListener {
            openUrlInBrowser(AuthExternalLinks.OK_URL)
        }
    }

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }
}

private fun Char.isCyrillic(): Boolean {
    return this in '\u0400'..'\u04FF' ||
            this in '\u0500'..'\u052F' ||
            this in '\u2DE0'..'\u2DFF' ||
            this in '\uA640'..'\uA69F'
}
