package com.example.moneyflowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.tcc.money.databinding.ActivityLoginBinding
import com.tcc.money.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReceita.setOnClickListener {
            Toast.makeText(this, "Botão Receita clicado", Toast.LENGTH_SHORT).show()
        }
        binding.btnDespesa.setOnClickListener {
            Toast.makeText(this, "Botão Despesa clicado", Toast.LENGTH_SHORT).show()
        }
        binding.btnPix.setOnClickListener {
            Toast.makeText(this, "Botão Pix clicado", Toast.LENGTH_SHORT).show()
        }

        binding.btnAdicionar.setOnClickListener {
            Toast.makeText(this, "Botão ADICIONAR clicado", Toast.LENGTH_SHORT).show()
        }

        binding.btnRetirar.setOnClickListener {
            Toast.makeText(this, "Botão RETIRAR clicado", Toast.LENGTH_SHORT).show()
        }
    }
}

