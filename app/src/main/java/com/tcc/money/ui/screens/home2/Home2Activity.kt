package com.example.moneyflowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Home2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Botões da primeira parte da tela
        val btnReceita: Button = findViewById(R.id.btnReceita)
        val btnDespesa: Button = findViewById(R.id.btnDespesa)
        val btnPix: Button = findViewById(R.id.btnPix)

        btnReceita.setOnClickListener {
            Toast.makeText(this, "Botão Receita clicado", Toast.LENGTH_SHORT).show()
        }
        btnDespesa.setOnClickListener {
            Toast.makeText(this, "Botão Despesa clicado", Toast.LENGTH_SHORT).show()
        }
        btnPix.setOnClickListener {
            Toast.makeText(this, "Botão Pix clicado", Toast.LENGTH_SHORT).show()
        }

        // Botões da segunda parte da tela
        val btnAdicionar: Button = findViewById(R.id.btnAdicionar)
        val btnRetirar: Button = findViewById(R.id.btnRetirar)

        btnAdicionar.setOnClickListener {
            Toast.makeText(this, "Botão ADICIONAR clicado", Toast.LENGTH_SHORT).show()
        }

        btnRetirar.setOnClickListener {
            Toast.makeText(this, "Botão RETIRAR clicado", Toast.LENGTH_SHORT).show()
        }
    }
}

