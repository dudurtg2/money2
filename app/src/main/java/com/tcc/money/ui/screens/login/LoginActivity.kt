package com.tcc.money.ui.screens.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tcc.money.R
import com.tcc.money.data.applications.LoginUseCase
import com.tcc.money.data.dto.Login
import com.tcc.money.databinding.ActivityLoginBinding
import com.tcc.money.ui.screens.cadastros.CadastroEtapa1Activity
import com.tcc.money.ui.screens.principal.PrincipalActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var loginUseCase: LoginUseCase

    private var senhaVisivel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.tvCriarConta.setOnClickListener {
            startActivity(Intent(this, CadastroEtapa1Activity::class.java))
        }
        binding.mainCardTest.setOnClickListener {
            startActivity(Intent(this, PrincipalActivity::class.java))
        }

        binding.ivTogglePassword.setOnClickListener {
            togglePassword()
        }

        binding.btnLogin.setOnClickListener {
            binding.progressBarLogin.visibility = android.view.View.VISIBLE
            lifecycleScope.launch {
                runCatching { login() }
                    .onSuccess {
                        startActivity(Intent(this@LoginActivity, PrincipalActivity::class.java))
                        finish()
                    }
                    .onFailure { e ->
                        Log.e("LoginUseCase", "Erro no login: ${e.message}")

                        Toast.makeText(
                            this@LoginActivity,
                            "Erro no login: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.progressBarLogin.visibility = android.view.View.GONE
                    }
            }
        }
    }

    private fun togglePassword() {
        senhaVisivel = !senhaVisivel
        if (senhaVisivel) {
            binding.etSenha.transformationMethod = null
            binding.ivTogglePassword.setImageResource(R.drawable.eye_open_icon)
        } else {
            binding.etSenha.transformationMethod =
                android.text.method.PasswordTransformationMethod.getInstance()
            binding.ivTogglePassword.setImageResource(R.drawable.eye_close_icon)
        }
        binding.etSenha.setSelection(binding.etSenha.text?.length ?: 0)
    }

    private suspend fun login() {
        if (!checkFields()) {
            throw Exception("Preencha todos os campos")
        } else {
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etSenha.text.toString()
            loginUseCase.execute(Login(email, senha))
        }
    }

    private fun checkFields(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        val senha = binding.etSenha.text.toString()
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show()
            false
        } else if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
}
