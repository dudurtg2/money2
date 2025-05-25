package com.tcc.money.ui.screens.cadastros

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.R

class CadastroEtapa2Activity : AppCompatActivity() {
    private var senhaVisivel = false
    private var confirmarVisivel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_etapa2)

        val etSenha = findViewById<EditText>(R.id.etSenha)
        val etConfirmar = findViewById<EditText>(R.id.etConfirmarSenha)
        val ivOlhoSenha = findViewById<ImageView>(R.id.ivToggleSenha)
        val ivOlhoConfirmar = findViewById<ImageView>(R.id.ivToggleConfirmar)
        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltar)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)

        ivOlhoSenha.setOnClickListener {
            senhaVisivel = !senhaVisivel
            etSenha.inputType = if (senhaVisivel)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            etSenha.setSelection(etSenha.text.length)
        }

        ivOlhoConfirmar.setOnClickListener {
            confirmarVisivel = !confirmarVisivel
            etConfirmar.inputType = if (confirmarVisivel)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            etConfirmar.setSelection(etConfirmar.text.length)
        }

        btnVoltar.setOnClickListener {
            finish()
        }

        btnCadastrar.setOnClickListener {
            // Aqui você pode só exibir um Toast ou redirecionar pra Home futuramente
        }
    }
}
