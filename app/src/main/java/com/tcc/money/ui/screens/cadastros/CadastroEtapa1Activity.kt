package com.tcc.money.ui.screens.cadastros

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.R
import com.tcc.money.databinding.ActivityCadastroEtapa1Binding

class CadastroEtapa1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroEtapa1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroEtapa1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProximo.setOnClickListener {
            val intent = Intent(this, CadastroEtapa2Activity::class.java)

           startActivity(intent)
        }
        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }
}
