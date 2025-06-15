package com.tcc.money.ui.screens.launch

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tcc.money.R

class LaunchActivity : AppCompatActivity() {

    private lateinit var tvValue: EditText
    private lateinit var btnReceita: Button
    private lateinit var btnDespesa: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        tvValue = findViewById(R.id.tvValue)
        btnReceita = findViewById(R.id.btnReceita)
        btnDespesa = findViewById(R.id.btnDespesa)

        btnReceita.setOnClickListener {
            toggleButtons(true)
        }

        btnDespesa.setOnClickListener {
            toggleButtons(false)
        }

        toggleButtons(true) // Receita vem selecionado por padrão
    }

    private fun toggleButtons(isReceita: Boolean) {
        val primary = ContextCompat.getColor(this, R.color.primary)
        val secondary = ContextCompat.getColor(this, R.color.secundary)

        if (isReceita) {
            btnReceita.setBackgroundResource(R.drawable.bg_button_selected)
            btnReceita.setTextColor(primary)

            btnDespesa.setBackgroundResource(R.drawable.bg_button_unselected)
            btnDespesa.setTextColor(secondary)
        } else {
            btnDespesa.setBackgroundResource(R.drawable.bg_button_selected)
            btnDespesa.setTextColor(primary)

            btnReceita.setBackgroundResource(R.drawable.bg_button_unselected)
            btnReceita.setTextColor(secondary)
        }
    }
}
