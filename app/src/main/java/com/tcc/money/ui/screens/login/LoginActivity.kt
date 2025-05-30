package com.tcc.money.ui.screens.login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.R
import com.tcc.money.databinding.ActivityLoginBinding
import com.tcc.money.ui.screens.cadastros.CadastroEtapa1Activity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var senhaVisivel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvCriarConta.setOnClickListener {
            val intent = Intent(this, CadastroEtapa1Activity::class.java)
            startActivity(intent)
        }

        // Funcionalidade do olhinho
        binding.ivTogglePassword.setOnClickListener {
            senhaVisivel = !senhaVisivel
            if (senhaVisivel) {
                binding.etSenha.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivTogglePassword.setImageResource(R.drawable.eye_open_icon)
            } else {
                binding.etSenha.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.ivTogglePassword.setImageResource(R.drawable.eye_close_icon)
            }
            binding.etSenha.setSelection(binding.etSenha.text.length)
        }
    }
}
