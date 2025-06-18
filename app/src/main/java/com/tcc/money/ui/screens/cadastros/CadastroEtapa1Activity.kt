package com.tcc.money.ui.screens.cadastros

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.data.dto.Cadastro
import com.tcc.money.databinding.ActivityCadastroEtapa1Binding
import com.tcc.money.ui.screens.cadastros.CadastroEtapa2Activity
import com.tcc.money.ui.screens.login.LoginActivity
import com.tcc.money.utils.validator.CadastroEtapa1Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CadastroEtapa1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroEtapa1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroEtapa1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProximo.setOnClickListener {
            if (validarCampos()) {
                val cadastro = Cadastro(
                    nome = binding.etNome.text.toString().trim(),
                    sobrenome = binding.etSobrenome.text.toString().trim(),
                    cpf = binding.etCpf.text.toString().trim(),
                    dataNascimento = binding.etDataNascimento.text.toString().trim()
                )

                val intent = Intent(this, CadastroEtapa2Activity::class.java)
                intent.putExtra("cadastro", cadastro)
                startActivity(intent)
            }
        }

        binding.btnVoltar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validarCampos(): Boolean {
        val nome = binding.etNome.text.toString().trim()
        val sobrenome = binding.etSobrenome.text.toString().trim()
        val cpf = binding.etCpf.text.toString().trim()
        val dataNascimento = binding.etDataNascimento.text.toString().trim()

        return CadastroEtapa1Validator.validarTodos(nome, sobrenome, cpf, dataNascimento)
    }
}

