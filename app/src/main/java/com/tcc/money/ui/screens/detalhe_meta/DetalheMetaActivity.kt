package com.tcc.money.ui.screens.detalhe_meta

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.R
import com.tcc.money.data.dto.MetasDTO
import com.tcc.money.ui.screens.editar_meta.EditarMetaActivity


class DetalheMetaActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnTransferir: Button
    private lateinit var btnEditar: Button

    private lateinit var imgMeta: ImageView
    private lateinit var txtNomeMeta: TextView
    private lateinit var txtValorAtual: TextView
    private lateinit var txtValorTotal: TextView
    private lateinit var txtDataLimite: TextView
    private lateinit var txtDescricao: TextView
    private lateinit var progressoMeta: ProgressBar

    private var valorAtual: Double = 0.0
    private lateinit var metaRecebida: MetasDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_meta)

        // IDs
        btnBack = findViewById(R.id.btnBack)
        btnTransferir = findViewById(R.id.btnTransferir)
        btnEditar = findViewById(R.id.btnEditar)

        imgMeta = findViewById(R.id.imgMeta)
        txtNomeMeta = findViewById(R.id.txtNomeMeta)
        txtValorAtual = findViewById(R.id.txtValorAtual)
        txtValorTotal = findViewById(R.id.txtValorTotal)
        txtDataLimite = findViewById(R.id.txtDataLimite)
        txtDescricao = findViewById(R.id.txtDescricao)
        progressoMeta = findViewById(R.id.progressoMeta)

        // Recebe a meta vinda da tela anterior
        metaRecebida = intent.getSerializableExtra("meta") as MetasDTO
        valorAtual = intent.getDoubleExtra("valorAtual", 0.0)

        // Preenche os dados na tela
        preencherDados()

        // Botão de voltar
        btnBack.setOnClickListener {
            finish()
        }

        // Botão Editar → leva pra tela EditarMetaActivity
        btnEditar.setOnClickListener {
            val intent = Intent(this, EditarMetaActivity::class.java)
            intent.putExtra("meta", metaRecebida)
            intent.putExtra("valorAtual", valorAtual)
            startActivity(intent)
        }

        // Botão Transferir (a ser implementado futuramente)
        btnTransferir.setOnClickListener {
            Toast.makeText(this, "Funcionalidade de transferência em desenvolvimento!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun preencherDados() {
        txtNomeMeta.text = metaRecebida.nome
        txtValorAtual.text = "R$ ${String.format("%.2f", valorAtual)}"
        txtValorTotal.text = "de R$ ${String.format("%.2f", metaRecebida.valor)}"
        txtDataLimite.text = metaRecebida.data
        txtDescricao.text = metaRecebida.descricao

        // Progresso
        val progresso = calcularProgresso(valorAtual, metaRecebida.valor)
        progressoMeta.progress = progresso

        // Imagem
        metaRecebida.imagemUri?.let {
            imgMeta.setImageURI(it)
        } ?: imgMeta.setImageResource(R.drawable.image_placeholder)
    }

    private fun calcularProgresso(atual: Double, total: Double): Int {
        return if (total > 0) {
            ((atual / total) * 100).toInt().coerceIn(0, 100)
        } else {
            0
        }
    }
}
