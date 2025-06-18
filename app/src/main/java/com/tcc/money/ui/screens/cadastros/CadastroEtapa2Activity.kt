package com.tcc.money.ui.screens.cadastros

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.data.dto.Cadastro
import com.tcc.money.databinding.ActivityCadastroEtapa2Binding
import com.tcc.money.ui.screens.principal.PrincipalActivity
import com.tcc.money.utils.validator.CadastroEtapa2Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CadastroEtapa2Activity : AppCompatActivity() {

    private var senhaVisivel = false
    private var confirmarVisivel = false
    private lateinit var binding: ActivityCadastroEtapa2Binding
    private lateinit var dadosEtapa1: Cadastro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroEtapa2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        dadosEtapa1 = intent.getSerializableExtra("cadastro") as Cadastro

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
            val intent = Intent(this, CadastroEtapa1Activity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCadastrar.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etSenha.text.toString().trim()
            val confirmarSenha = binding.etConfirmarSenha.text.toString().trim()

            if (CadastroEtapa2Validator.validarTodos(email, senha, confirmarSenha)) {
                Toast.makeText(this, "Cadastro finalizado com sucesso!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, PrincipalActivity::class.java)
                intent.putExtra("cadastro", dadosEtapa1)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Verifique os campos digitados.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
