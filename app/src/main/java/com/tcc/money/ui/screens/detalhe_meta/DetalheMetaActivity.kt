package com.tcc.money.ui.screens.detalhe_meta

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.R
import com.tcc.money.data.dto.MetasDTO
import com.tcc.money.databinding.ActivityDetalheMetaBinding
import com.tcc.money.ui.screens.editar_meta.EditarMetaActivity


class DetalheMetaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalheMetaBinding

    private var valorAtual: Double = 0.0
    private lateinit var metaRecebida: MetasDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalheMetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        metaRecebida = intent.getSerializableExtra("meta") as MetasDTO
        valorAtual = intent.getDoubleExtra("valorAtual", 0.0)

        preencherDados()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnEditar.setOnClickListener {
            val intent = Intent(this, EditarMetaActivity::class.java)
            intent.putExtra("meta", metaRecebida)
            intent.putExtra("valorAtual", valorAtual)
            startActivity(intent)
        }

        binding.btnTransferir.setOnClickListener {
            Toast.makeText(this, "Funcionalidade de transferência em desenvolvimento!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun preencherDados() {
        binding.txtNomeMeta.text = metaRecebida.nome
        binding.txtValorAtual.text = "R$ ${String.format("%.2f", valorAtual)}"
        binding.txtValorTotal.text = "de R$ ${String.format("%.2f", metaRecebida.valor)}"
        binding.txtDataLimite.text = metaRecebida.data
        binding.txtDescricao.text = metaRecebida.descricao

        val progresso = calcularProgresso(valorAtual, metaRecebida.valor)
        binding.progressoMeta.progress = progresso

        metaRecebida.imagemUri?.let {
            binding.imgMeta.setImageURI(it)
        } ?: binding.imgMeta.setImageResource(R.drawable.image_placeholder)
    }

    private fun calcularProgresso(atual: Double, total: Double): Int {
        return if (total > 0) {
            ((atual / total) * 100).toInt().coerceIn(0, 100)
        } else {
            0
        }
    }
}
