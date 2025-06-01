package com.tcc.money.ui.screens.cadastros

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.R
import com.tcc.money.databinding.ActivityCadastroEtapa1Binding
import com.tcc.money.databinding.ActivityCadastroEtapa2Binding

class CadastroEtapa2Activity : AppCompatActivity() {
    private var senhaVisivel = false
    private var confirmarVisivel = false
    private lateinit var binding: ActivityCadastroEtapa2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroEtapa2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.ivToggleSenha.setOnClickListener {
            senhaVisivel = !senhaVisivel
            binding.etSenha.inputType = if (senhaVisivel)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.etSenha.setSelection(binding.etSenha.text.length)
        }

        binding.ivToggleConfirmar.setOnClickListener {
            confirmarVisivel = !confirmarVisivel
            binding.etConfirmarSenha.inputType = if (confirmarVisivel)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.etConfirmarSenha.setSelection(binding.etConfirmarSenha.text.length)
        }

        binding.btnVoltar.setOnClickListener {
            finish()
        }

        binding.btnCadastrar.setOnClickListener {
            // Aqui você pode só exibir um Toast ou redirecionar pra Home futuramente
        }
    }
}
