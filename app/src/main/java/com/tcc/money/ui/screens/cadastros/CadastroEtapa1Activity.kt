package com.tcc.money.ui.screens.cadastros

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.R

class CadastroEtapa1Activity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_etapa1)

        val btnProximo = findViewById<Button>(R.id.btnProximo)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        btnProximo.setOnClickListener {
            val intent = Intent(this, CadastroEtapa2Activity::class.java)
           startActivity(intent)
        }

        btnVoltar.setOnClickListener {
            finish() // volta pra tela anterior
        }
    }
}
