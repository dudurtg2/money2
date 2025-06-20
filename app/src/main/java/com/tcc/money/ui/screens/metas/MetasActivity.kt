package com.tcc.money.ui.screens.metas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.R
import com.tcc.money.data.dto.MetasDTO
import com.tcc.money.ui.screens.criar_meta.CriarMetaActivity
import com.tcc.money.ui.screens.detalhe_meta.DetalheMetaActivity

class MetasActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnNovaMeta: Button
    private lateinit var progressMetaPrincipal: ProgressBar
    private lateinit var progressOutraMeta: ProgressBar
    private lateinit var imgMetaPrincipal: LinearLayout
    private lateinit var imgMeta: LinearLayout

    private lateinit var txtNomeMetaPrincipal: TextView
    private lateinit var txtValorAtualPrincipal: TextView
    private lateinit var txtValorTotalPrincipal: TextView

    private lateinit var txtNomeMeta: TextView
    private lateinit var txtValorAtualMeta: TextView
    private lateinit var txtValorTotalMeta: TextView

    companion object {
        const val REQUEST_CRIAR_META = 2001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metas)

        // IDs
        btnBack = findViewById(R.id.btnBack)
        btnNovaMeta = findViewById(R.id.btnNovaMeta)
        progressMetaPrincipal = findViewById(R.id.progressMetaPrincipal)
        progressOutraMeta = findViewById(R.id.progressOutraMeta)
        imgMetaPrincipal = findViewById(R.id.imgMetaPrincipal)
        imgMeta = findViewById(R.id.imgMeta)

        txtNomeMetaPrincipal = findViewById(R.id.txtNomeMeta)
        txtValorAtualPrincipal = findViewById(R.id.txtValorAtual)
        txtValorTotalPrincipal = findViewById(R.id.txtValorTotal)

        txtNomeMeta = findViewById(R.id.txtNomeMeta)
        txtValorAtualMeta = findViewById(R.id.txtValorAtual)
        txtValorTotalMeta = findViewById(R.id.txtValorTotal)

        // Voltar
        btnBack.setOnClickListener {
            finish()
        }

        // Nova meta
        btnNovaMeta.setOnClickListener {
            val intent = Intent(this, CriarMetaActivity::class.java)
            startActivityForResult(intent, REQUEST_CRIAR_META)
        }

        // Detalhe meta principal
        imgMetaPrincipal.setOnClickListener {
            val intent = Intent(this, DetalheMetaActivity::class.java)
            startActivity(intent)
        }

        // Detalhe outra meta
        imgMeta.setOnClickListener {
            val intent = Intent(this, DetalheMetaActivity::class.java)
            startActivity(intent)
        }

        // Progresso inicial (padrão)
        progressMetaPrincipal.progress = calcularProgresso(300.0, 1000.0)
        progressOutraMeta.progress = calcularProgresso(0.0, 500.0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CRIAR_META && resultCode == RESULT_OK) {
            val meta = data?.getSerializableExtra("meta") as? MetasDTO

            meta?.let {
                if (it.fixada) {
                    // Meta fixada → vai para o card de meta principal
                    imgMetaPrincipal.visibility = View.VISIBLE
                    txtNomeMetaPrincipal.text = it.nome
                    txtValorAtualPrincipal.text = "R$ %.2f".format(it.valor)
                    txtValorTotalPrincipal.text = "de R$ %.2f".format(it.valor)

                    progressMetaPrincipal.progress = calcularProgresso(it.valor, it.valor)
                } else {
                    // Meta NÃO fixada → vai para outras metas
                    imgMeta.visibility = View.VISIBLE
                    txtNomeMeta.text = it.nome
                    txtValorAtualMeta.text = "R$ %.2f".format(it.valor)
                    txtValorTotalMeta.text = "de R$ %.2f".format(it.valor)

                    progressOutraMeta.progress = calcularProgresso(it.valor, it.valor)
                }
            }
        }
    }

    // Função de cálculo de progresso
    fun calcularProgresso(arrecadado: Double, total: Double): Int {
        return if (total > 0) {
            (arrecadado / total * 100).toInt()
        } else {
            0
        }
    }
}
