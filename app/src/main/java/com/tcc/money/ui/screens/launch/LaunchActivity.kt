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
import com.tcc.money.databinding.ActivityLaunchBinding
import com.tcc.money.ui.screens.launch_expense.LancamentoDespesaActivity
import com.tcc.money.ui.screens.launch_revenue.LancamentoDetalhadoActivity
import com.tcc.money.utils.validator.LaunchValidator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaunchBinding
    private var tipoSelecionado: String = "Receita"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        setupListeners()
    }


    private fun setupListeners() {
        binding.btnReceita.setOnClickListener {
            toggleButtons(isReceita = true)
            if (LaunchValidator.validarTodos(binding.tvValue.text.toString())) {
                val dto = LaunchDTO(
                    valor = binding.tvValue.text.toString().trim(),
                    tipo = "Receita"
                )
                val intent = Intent(this, LancamentoDetalhadoActivity::class.java)
                intent.putExtra("launch", dto)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Preencha um valor válido!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDespesa.setOnClickListener {
            toggleButtons(isReceita = false)
            if (LaunchValidator.validarTodos(binding.tvValue.text.toString())) {
                val dto = LaunchDTO(
                    valor = binding.tvValue.text.toString().trim(),
                    tipo = "Despesa"
                )
                val intent = Intent(this, LancamentoDespesaActivity::class.java)
                intent.putExtra("launch", dto)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Preencha um valor válido!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun toggleButtons(isReceita: Boolean) {
        val primary = ContextCompat.getColor(this, R.color.primary)
        val secundary = ContextCompat.getColor(this, R.color.secundary)

        if (isReceita) {
            tipoSelecionado = "Receita"
            binding.btnReceita.setBackgroundResource(R.drawable.bg_button_selected)
            binding.btnReceita.setTextColor(primary)

            binding.btnDespesa.setBackgroundResource(R.drawable.bg_button_unselected)
            binding.btnDespesa.setTextColor(secundary)
        } else {
            tipoSelecionado = "Despesa"
            binding.btnDespesa.setBackgroundResource(R.drawable.bg_button_selected)
            binding.btnDespesa.setTextColor(primary)

            binding.btnReceita.setBackgroundResource(R.drawable.bg_button_unselected)
            binding.btnReceita.setTextColor(secundary)
        }
    }
}
