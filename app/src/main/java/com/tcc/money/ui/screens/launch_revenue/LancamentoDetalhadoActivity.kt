package com.tcc.money.ui.screens.launch

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
import com.tcc.money.utils.validator.LancamentoDetalhadoValidator

class LancamentoDetalhadoActivity : AppCompatActivity() {

    private lateinit var btnTipoValor: Button
    private lateinit var btnEditarValor: Button
    private lateinit var btnBack: ImageView
    private lateinit var tvValorDigitado: TextView
    private lateinit var btnDetalharDepois: Button
    private lateinit var btnHoraNotificacao: Button
    private lateinit var layoutDestinos: LinearLayout
    private lateinit var layoutCategorias: LinearLayout
    private lateinit var edtDescricao: EditText
    private lateinit var btnRecorrente: Button
    private lateinit var btnParcelado: Button
    private lateinit var layoutExtra: LinearLayout
    private lateinit var btnLancar: Button

    private var destinoSelecionado: Int? = null
    private var categoriaSelecionada: Int? = null
    private var recorrenteSelecionado = false
    private var parceladoSelecionado = false
    private var qtdParcelas: Int? = null
    private var valorTotal = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lancamento_detalhado)

        bindViews()
        setupListeners()

        // Receber dados do DTO
        val dto = intent.getSerializableExtra("launch") as? LaunchDTO
        val tipo = dto?.tipo ?: "Receita"
        val valor = dto?.valor ?: "00,00"

        btnTipoValor.text = tipo
        tvValorDigitado.text = "R$ $valor"
        valorTotal = valor.replace(",", ".").toDoubleOrNull() ?: 0.0

        gerarDestinosMock()
        gerarCategoriasMock()
    }

    private fun bindViews() {
        btnTipoValor = findViewById(R.id.btnTipoValor)
        btnEditarValor = findViewById(R.id.btnEditarValor)
        btnBack = findViewById(R.id.btnBack)
        tvValorDigitado = findViewById(R.id.tvValorDigitado)
        btnDetalharDepois = findViewById(R.id.btnDetalharDepois)
        btnHoraNotificacao = findViewById(R.id.btnHoraNotificacao)
        layoutDestinos = findViewById(R.id.layoutDestinos)
        layoutCategorias = findViewById(R.id.layoutCategorias)
        edtDescricao = findViewById(R.id.edtDescricao)
        btnRecorrente = findViewById(R.id.btnRecorrente)
        btnParcelado = findViewById(R.id.btnParcelado)
        layoutExtra = findViewById(R.id.layoutExtra)
        btnLancar = findViewById(R.id.btnLancar)
    }

    private fun setupListeners() {
        btnEditarValor.setOnClickListener {
            finish()
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnDetalharDepois.setOnClickListener {
            Toast.makeText(this, "Lançamento rápido agendado", Toast.LENGTH_SHORT).show()
            btnLancar.visibility = View.VISIBLE
            layoutCategorias.visibility = View.GONE
            layoutDestinos.visibility = View.GONE
        }

        btnHoraNotificacao.setOnClickListener {
            Toast.makeText(this, "Notificar em 1h (placeholder)", Toast.LENGTH_SHORT).show()
        }

        btnRecorrente.setOnClickListener {
            recorrenteSelecionado = true
            parceladoSelecionado = false
            qtdParcelas = null
            atualizarRecorrenteOuParcelado()
        }

        btnParcelado.setOnClickListener {
            recorrenteSelecionado = false
            parceladoSelecionado = true
            atualizarRecorrenteOuParcelado()
        }

        btnLancar.setOnClickListener {
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
                atualizarSelecao(layoutDestinos, index)
            }
            layoutDestinos.addView(button)
        }
    }

    private fun gerarCategoriasMock() {
        val categorias = listOf("Transferência", "Venda", "Freelance")
        categorias.forEachIndexed { index, nome ->
            val button = criarBotaoSelecionavel(nome) {
                categoriaSelecionada = index
                atualizarSelecao(layoutCategorias, index)
                btnLancar.visibility = View.VISIBLE
            }
            layoutCategorias.addView(button)
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
        layoutExtra.removeAllViews()

        if (recorrenteSelecionado) {
            btnRecorrente.setBackgroundResource(R.drawable.bg_button_selected)
            btnRecorrente.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            btnParcelado.setBackgroundResource(R.drawable.bg_button_unselected)
            btnParcelado.setTextColor(ContextCompat.getColor(this, R.color.primary))

            val info = TextView(this).apply {
                text = "Mensal\nTodo dia ${java.time.LocalDate.now().dayOfMonth}"
                setTextColor(ContextCompat.getColor(this@LancamentoDetalhadoActivity, R.color.primary))
            }
            layoutExtra.addView(info)

        } else if (parceladoSelecionado) {
            btnParcelado.setBackgroundResource(R.drawable.bg_button_selected)
            btnParcelado.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            btnRecorrente.setBackgroundResource(R.drawable.bg_button_unselected)
            btnRecorrente.setTextColor(ContextCompat.getColor(this, R.color.primary))

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

            layoutExtra.addView(input)
            layoutExtra.addView(valorPorParcela)
        }
    }

    private fun validarCampos(): Boolean {
        return LancamentoDetalhadoValidator.validarTodos(
            destino = destinoSelecionado,
            categoria = categoriaSelecionada,
            descricao = edtDescricao.text.toString(),
            parcelado = parceladoSelecionado,
            qtdParcelas = qtdParcelas,
            valor = valorTotal
        )
    }
}
