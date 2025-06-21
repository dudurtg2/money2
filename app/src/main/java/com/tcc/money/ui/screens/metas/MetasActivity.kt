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
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import com.tcc.money.databinding.ActivityLaunchBinding
import com.tcc.money.databinding.ActivityMetasBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MetasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMetasBinding


    companion object {
        const val REQUEST_CRIAR_META = 2001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metas)


        // Voltar
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Nova meta
        binding.btnNovaMeta.setOnClickListener {
            val intent = Intent(this, CriarMetaActivity::class.java)
            startActivityForResult(intent, REQUEST_CRIAR_META)
        }

        // Detalhe meta principal
        binding.imgMetaPrincipal.setOnClickListener {
            val intent = Intent(this, DetalheMetaActivity::class.java)
            startActivity(intent)
        }

        // Detalhe outra meta
        binding.imgMeta.setOnClickListener {
            val intent = Intent(this, DetalheMetaActivity::class.java)
            startActivity(intent)
        }

        // Progresso inicial (padrão)
        binding.progressMetaPrincipal.progress = calcularProgresso(300.0, 1000.0)
        binding.progressOutraMeta.progress = calcularProgresso(0.0, 500.0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CRIAR_META && resultCode == RESULT_OK) {
            val meta = data?.getSerializableExtra("meta") as? MetasDTO

            meta?.let {
                if (it.fixada) {
                    // Meta fixada → vai para o card de meta principal
                    binding.imgMetaPrincipal.visibility = View.VISIBLE
                    binding.txtNomeMetaPrincipal.text = it.nome
                    binding.txtValorAtualPrincipal.text = "R$ %.2f".format(it.valor)
                    binding. txtValorTotalPrincipal.text = "de R$ %.2f".format(it.valor)

                    binding.progressMetaPrincipal.progress = calcularProgresso(it.valor, it.valor)
                } else {
                    // Meta NÃO fixada → vai para outras metas
                    binding.imgMeta.visibility = View.VISIBLE
                    binding.txtNomeMeta.text = it.nome
                    binding.txtValorAtualPrincipal.text = "R$ %.2f".format(it.valor)
                    binding.txtValorTotalPrincipal.text = "de R$ %.2f".format(it.valor)

                    binding.progressOutraMeta.progress = calcularProgresso(it.valor, it.valor)
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
