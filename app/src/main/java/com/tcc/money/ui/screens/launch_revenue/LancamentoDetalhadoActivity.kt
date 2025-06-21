package com.tcc.money.ui.screens.launch_revenue

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tcc.money.R
import com.tcc.money.data.dto.LaunchDTO
import com.tcc.money.databinding.ActivityLancamentoDetalhadoBinding
import com.tcc.money.utils.validator.LancamentoDetalhadoValidator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LancamentoDetalhadoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLancamentoDetalhadoBinding


    private var destinoSelecionado: Int? = null
    private var categoriaSelecionada: Int? = null
    private var recorrenteSelecionado = false
    private var parceladoSelecionado = false
    private var qtdParcelas: Int? = null
    private var valorTotal = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lancamento_detalhado)

        setupListeners()

        // Receber dados do DTO
        val dto = intent.getSerializableExtra("launch") as? LaunchDTO
        val tipo = dto?.tipo ?: "Receita"
        val valor = dto?.valor ?: "00,00"

        binding.btnTipoValor.text = tipo
        binding.tvValorDigitado.text = "R$ $valor"
        valorTotal = valor.replace(",", ".").toDoubleOrNull() ?: 0.0

        gerarDestinosMock()
        gerarCategoriasMock()
    }



    private fun setupListeners() {
        binding.btnEditarValor.setOnClickListener {
            finish()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnDetalharDepois.setOnClickListener {
            Toast.makeText(this, "Lançamento rápido agendado", Toast.LENGTH_SHORT).show()
            binding.btnLancar.visibility = View.VISIBLE
            binding.layoutCategorias.visibility = View.GONE
            binding.layoutDestinos.visibility = View.GONE
        }

        binding.btnHoraNotificacao.setOnClickListener {
            Toast.makeText(this, "Notificar em 1h (placeholder)", Toast.LENGTH_SHORT).show()
        }

        binding.btnRecorrente.setOnClickListener {
            recorrenteSelecionado = true
            parceladoSelecionado = false
            qtdParcelas = null
            atualizarRecorrenteOuParcelado()
        }

        binding.btnParcelado.setOnClickListener {
            recorrenteSelecionado = false
            parceladoSelecionado = true
            atualizarRecorrenteOuParcelado()
        }

        binding.btnLancar.setOnClickListener {
            if (validarCampos()) {
                Toast.makeText(this, "Lançamento realizado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gerarDestinosMock() {
        val destinos = listOf("Cash App", "PayPal", "Nubank")
        destinos.forEachIndexed { index, nome ->
            val button = criarBotaoSelecionavel("$nome\nR$0000,00") {
                destinoSelecionado = index
                atualizarSelecao(binding.layoutDestinos, index)
            }
            binding.layoutDestinos.addView(button)
        }
    }

    private fun gerarCategoriasMock() {
        val categorias = listOf("Transferência", "Venda", "Freelance")
        categorias.forEachIndexed { index, nome ->
            val button = criarBotaoSelecionavel(nome) {
                categoriaSelecionada = index
                atualizarSelecao(binding.layoutCategorias, index)
                binding.btnLancar.visibility = View.VISIBLE
            }
            binding.layoutCategorias.addView(button)
        }
    }

    private fun criarBotaoSelecionavel(texto: String, onClick: () -> Unit): Button {
        return Button(this).apply {
            text = texto
            setPadding(24, 16, 24, 16)
            background = ContextCompat.getDrawable(context, R.drawable.bg_button_unselected)
            setTextColor(ContextCompat.getColor(context, R.color.primary))
            setOnClickListener { onClick() }
        }
    }

    private fun atualizarSelecao(layout: LinearLayout, selecionado: Int) {
        for (i in 0 until layout.childCount) {
            val btn = layout.getChildAt(i) as Button
            if (i == selecionado) {
                btn.background = ContextCompat.getDrawable(this, R.drawable.bg_button_selected)
                btn.setTextColor(ContextCompat.getColor(this, android.R.color.white))
                btn.setTypeface(null, Typeface.BOLD)
            } else {
                btn.background = ContextCompat.getDrawable(this, R.drawable.bg_button_unselected)
                btn.setTextColor(ContextCompat.getColor(this, R.color.primary))
                btn.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    private fun atualizarRecorrenteOuParcelado() {
        binding.layoutExtra.removeAllViews()

        if (recorrenteSelecionado) {
            binding.btnRecorrente.setBackgroundResource(R.drawable.bg_button_selected)
            binding.btnRecorrente.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            binding.btnParcelado.setBackgroundResource(R.drawable.bg_button_unselected)
            binding.btnParcelado.setTextColor(ContextCompat.getColor(this, R.color.primary))

            val info = TextView(this).apply {
                text = "Mensal\nTodo dia ${java.time.LocalDate.now().dayOfMonth}"
                setTextColor(ContextCompat.getColor(this@LancamentoDetalhadoActivity, R.color.primary))
            }
            binding.layoutExtra.addView(info)

        } else if (parceladoSelecionado) {
            binding.btnParcelado.setBackgroundResource(R.drawable.bg_button_selected)
            binding.btnParcelado.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            binding.btnRecorrente.setBackgroundResource(R.drawable.bg_button_unselected)
            binding.btnRecorrente.setTextColor(ContextCompat.getColor(this, R.color.primary))

            val input = EditText(this).apply {
                hint = "x Parcelas"
                inputType = InputType.TYPE_CLASS_NUMBER
            }

            val valorPorParcela = TextView(this).apply {
                setTextColor(ContextCompat.getColor(this@LancamentoDetalhadoActivity, R.color.primary))
            }

            input.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val qtd = s.toString().toIntOrNull()
                    qtdParcelas = qtd
                    if (qtd != null && qtd > 0) {
                        val valor = valorTotal / qtd
                        valorPorParcela.text = "Valor de cada parcela:\nR$ %.2f".format(valor)
                    } else {
                        valorPorParcela.text = ""
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            binding.layoutExtra.addView(input)
            binding.layoutExtra.addView(valorPorParcela)
        }
    }

    private fun validarCampos(): Boolean {
        return LancamentoDetalhadoValidator.validarTodos(
            destino = destinoSelecionado,
            categoria = categoriaSelecionada,
            descricao = binding.edtDescricao.text.toString(),
            parcelado = parceladoSelecionado,
            qtdParcelas = qtdParcelas,
            valor = valorTotal
        )
    }
}
