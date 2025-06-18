package com.tcc.money.ui.screens.launch

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

class LancamentoDespesaActivity : AppCompatActivity() {

    private lateinit var btnTipoValor: Button
    private lateinit var btnEditarValor: Button
    private lateinit var tvValorDigitado: TextView
    private lateinit var btnDetalharDepois: Button
    private lateinit var btnHoraNotificacao: Button
    private lateinit var layoutOrigens: LinearLayout
    private lateinit var layoutCategorias: LinearLayout
    private lateinit var layoutSubcategorias: LinearLayout
    private lateinit var edtDescricao: EditText
    private lateinit var btnRecorrente: Button
    private lateinit var btnParcelado: Button
    private lateinit var layoutExtra: LinearLayout
    private lateinit var btnLancar: Button

    private var origemSelecionada: Int? = null
    private var categoriaSelecionada: Int? = null
    private var subcategoriaSelecionada: Int? = null
    private var recorrenteSelecionado = false
    private var parceladoSelecionado = false
    private var valorTotal = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lancamento_despesa)

        bindViews()

        // Valor recebido (mockado por enquanto)
        val tipo = intent.getStringExtra("tipo") ?: "Despesa"
        val valor = intent.getStringExtra("valor") ?: "00,00"
        btnTipoValor.text = tipo
        tvValorDigitado.text = "R$ $valor"
        valorTotal = valor.replace(",", ".").toDoubleOrNull() ?: 0.0

        btnEditarValor.setOnClickListener { finish() }

        btnDetalharDepois.setOnClickListener {
            Toast.makeText(this, "Despesa salva pra depois", Toast.LENGTH_SHORT).show()
            btnLancar.visibility = View.VISIBLE
            layoutCategorias.visibility = View.GONE
            layoutOrigens.visibility = View.GONE
            layoutSubcategorias.visibility = View.GONE
        }

        btnHoraNotificacao.setOnClickListener {
            Toast.makeText(this, "Notificação agendada (placeholder)", Toast.LENGTH_SHORT).show()
        }

        gerarOrigensMock()
        gerarCategoriasMock()
        gerarSubcategoriasMock()

        btnRecorrente.setOnClickListener {
            recorrenteSelecionado = true
            parceladoSelecionado = false
            atualizarRecorrenteOuParcelado()
        }

        btnParcelado.setOnClickListener {
            recorrenteSelecionado = false
            parceladoSelecionado = true
            atualizarRecorrenteOuParcelado()
        }

        btnLancar.setOnClickListener {
            Toast.makeText(this, "Despesa lançada!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun bindViews() {
        btnTipoValor = findViewById(R.id.btnTipoValor)
        btnEditarValor = findViewById(R.id.btnEditarValor)
        tvValorDigitado = findViewById(R.id.tvValorDigitado)
        btnDetalharDepois = findViewById(R.id.btnDetalharDepois)
        btnHoraNotificacao = findViewById(R.id.btnHoraNotificacao)
        layoutOrigens = findViewById(R.id.layoutOrigens)
        layoutCategorias = findViewById(R.id.layoutCategorias)
        layoutSubcategorias = findViewById(R.id.layoutSubcategorias)
        edtDescricao = findViewById(R.id.edtDescricao)
        btnRecorrente = findViewById(R.id.btnRecorrente)
        btnParcelado = findViewById(R.id.btnParcelado)
        layoutExtra = findViewById(R.id.layoutExtra)
        btnLancar = findViewById(R.id.btnLancar)
    }

    private fun gerarOrigensMock() {
        val origens = listOf("Conta Corrente", "Carteira", "Inter")
        origens.forEachIndexed { index, nome ->
            val button = criarBotaoSelecionavel(nome) {
                origemSelecionada = index
                atualizarSelecao(layoutOrigens, index)
            }
            layoutOrigens.addView(button)
        }
    }

    private fun gerarCategoriasMock() {
        val categorias = listOf("Alimentação", "Transporte", "Educação")
        categorias.forEachIndexed { index, nome ->
            val button = criarBotaoSelecionavel(nome) {
                categoriaSelecionada = index
                atualizarSelecao(layoutCategorias, index)
                btnLancar.visibility = View.VISIBLE
            }
            layoutCategorias.addView(button)
        }
    }

    private fun gerarSubcategoriasMock() {
        val tags = listOf("iFood", "Uber", "Cursos")
        tags.forEachIndexed { index, nome ->
            val button = criarBotaoSelecionavel(nome) {
                subcategoriaSelecionada = index
                atualizarSelecao(layoutSubcategorias, index)
            }
            layoutSubcategorias.addView(button)
        }
    }

    private fun criarBotaoSelecionavel(texto: String, onClick: () -> Unit): Button {
        return Button(this).apply {
            text = texto
            setPadding(24, 12, 24, 12)
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
        layoutExtra.removeAllViews()

        if (recorrenteSelecionado) {
            val info = TextView(this).apply {
                text = "Mensal\nTodo dia ${java.time.LocalDate.now().dayOfMonth}"
                setTextColor(ContextCompat.getColor(this@LancamentoDespesaActivity, R.color.primary))
            }
            layoutExtra.addView(info)
        } else if (parceladoSelecionado) {
            val input = EditText(this).apply {
                hint = "x Parcelas"
                inputType = InputType.TYPE_CLASS_NUMBER
            }

            val valorPorParcela = TextView(this).apply {
                setTextColor(ContextCompat.getColor(this@LancamentoDespesaActivity, R.color.primary))
            }

            input.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val qtd = s.toString().toIntOrNull()
                    if (qtd != null && qtd > 0) {
                        val valor = valorTotal / qtd
                        valorPorParcela.text = "Valor por parcela:\nR$ %.2f".format(valor)
                    } else {
                        valorPorParcela.text = ""
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            layoutExtra.addView(input)
            layoutExtra.addView(valorPorParcela)
        }
    }
}
