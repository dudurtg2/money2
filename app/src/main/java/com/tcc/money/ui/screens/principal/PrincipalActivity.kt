package com.example.moneyflowapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botões atualizados conforme os novos IDs do XML
        binding.entryValuesTxt.setOnClickListener {
            Toast.makeText(this, "Botão Entrada clicado", Toast.LENGTH_SHORT).show()
        }

        binding.entryValuesFilterTxt.setOnClickListener {
            Toast.makeText(this, "Botão Filtro clicado", Toast.LENGTH_SHORT).show()
        }

        binding.ivProfile.setOnClickListener {
            Toast.makeText(this, "Ícone de perfil clicado", Toast.LENGTH_SHORT).show()
        }

        binding.ivNottification.setOnClickListener {
            Toast.makeText(this, "Botão de notificações clicado", Toast.LENGTH_SHORT).show()
        }
    }
}

