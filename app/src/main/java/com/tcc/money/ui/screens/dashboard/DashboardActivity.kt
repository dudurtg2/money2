package com.tcc.money.ui.screens.dashboard

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var txtSaldoTotal: TextView
    private lateinit var txtEntrou: TextView
    private lateinit var txtSaiu: TextView
    private lateinit var spinnerSaldo: Spinner
    private lateinit var spinnerDespesas: Spinner
    private lateinit var spinnerGrafico: Spinner
    private lateinit var layoutBancos: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Bind views
        txtSaldoTotal = findViewById(R.id.txtSaldoTotal)
        txtEntrou = findViewById(R.id.txtEntrou)
        txtSaiu = findViewById(R.id.txtSaiu)
        spinnerSaldo = findViewById(R.id.spinnerFiltroSaldo)
        spinnerDespesas = findViewById(R.id.spinnerFiltroDespesas)
        spinnerGrafico = findViewById(R.id.spinnerFiltroGrafico)
        layoutBancos = findViewById(R.id.layoutBancos)

        findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            finish()
        }

        setupSpinners()
        carregarDados()
    }

    private fun setupSpinners() {
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                carregarDados()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerSaldo.onItemSelectedListener = listener
        spinnerDespesas.onItemSelectedListener = listener
        spinnerGrafico.onItemSelectedListener = listener
    }

    private fun carregarDados() {
        // Simulação de valores
        val saldoTotal = 5000.00
        val entrou = 3000.00
        val saiu = 2000.00

        txtSaldoTotal.text = "R$%.2f".format(saldoTotal)
        txtEntrou.text = "+R$%.2f".format(entrou)
        txtSaiu.text = "-R$%.2f".format(saiu)

        // Bancos mock
        val bancos = listOf("Dinheiro" to 1000.0, "PayPal" to 500.0)

        layoutBancos.removeAllViews()
        for ((nome, valor) in bancos) {
            val txt = TextView(this)
            txt.text = "$nome: -R$%.2f".format(valor)
            txt.setPadding(8, 8, 8, 8)
            layoutBancos.addView(txt)
        }

        // O gráfico você pode atualizar com MPAndroidChart depois
    }
}
