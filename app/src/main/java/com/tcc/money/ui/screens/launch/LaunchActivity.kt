package com.tcc.money.ui.screens.launch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tcc.money.R
import com.tcc.money.data.dto.LaunchDTO
import com.tcc.money.ui.screens.launch.LancamentoDespesaActivity
import com.tcc.money.ui.screens.launch.LancamentoDetalhadoActivity
import com.tcc.money.utils.validator.LaunchValidator

class LaunchActivity : AppCompatActivity() {

    private lateinit var btnReceita: Button
    private lateinit var btnDespesa: Button
    private lateinit var btnBack: ImageView
    private lateinit var tvValue: EditText

    private var tipoSelecionado: String = "Receita"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        bindViews()
        setupListeners()
    }

    private fun bindViews() {
        btnReceita = findViewById(R.id.btnReceita)
        btnDespesa = findViewById(R.id.btnDespesa)
        btnBack = findViewById(R.id.btnBack)
        tvValue = findViewById(R.id.tvValue)
    }

    private fun setupListeners() {
        btnReceita.setOnClickListener {
            toggleButtons(isReceita = true)
            if (LaunchValidator.validarTodos(tvValue.text.toString())) {
                val dto = LaunchDTO(
                    valor = tvValue.text.toString().trim(),
                    tipo = "Receita"
                )
                val intent = Intent(this, LancamentoDetalhadoActivity::class.java)
                intent.putExtra("launch", dto)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Preencha um valor válido!", Toast.LENGTH_SHORT).show()
            }
        }

        btnDespesa.setOnClickListener {
            toggleButtons(isReceita = false)
            if (LaunchValidator.validarTodos(tvValue.text.toString())) {
                val dto = LaunchDTO(
                    valor = tvValue.text.toString().trim(),
                    tipo = "Despesa"
                )
                val intent = Intent(this, LancamentoDespesaActivity::class.java)
                intent.putExtra("launch", dto)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Preencha um valor válido!", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun toggleButtons(isReceita: Boolean) {
        val primary = ContextCompat.getColor(this, R.color.primary)
        val secundary = ContextCompat.getColor(this, R.color.secundary)

        if (isReceita) {
            tipoSelecionado = "Receita"
            btnReceita.setBackgroundResource(R.drawable.bg_button_selected)
            btnReceita.setTextColor(primary)

            btnDespesa.setBackgroundResource(R.drawable.bg_button_unselected)
            btnDespesa.setTextColor(secundary)
        } else {
            tipoSelecionado = "Despesa"
            btnDespesa.setBackgroundResource(R.drawable.bg_button_selected)
            btnDespesa.setTextColor(primary)

            btnReceita.setBackgroundResource(R.drawable.bg_button_unselected)
            btnReceita.setTextColor(secundary)
        }
    }
}
