package com.tcc.money.ui.screens.editar_meta


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.R
import com.tcc.money.data.dto.MetasDTO
import com.tcc.money.databinding.ActivityEditarMetaBinding
import com.tcc.money.ui.screens.detalhe_meta.DetalheMetaActivity
import com.tcc.money.ui.screens.metas.MetasActivity
import com.tcc.money.utils.validator.MetasValidator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditarMetaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarMetaBinding

    private var imagemSelecionadaUri: Uri? = null
    private val REQUEST_IMAGE_PICK = 1010

    private lateinit var metaRecebida: MetasDTO
    private var valorAtual: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditarMetaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        metaRecebida = intent.getSerializableExtra("meta") as MetasDTO
        valorAtual = intent.getDoubleExtra("valorAtual", 0.0)

        preencherDados()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        binding.btnSalvarMeta.setOnClickListener {
            val nome = binding.editNomeMeta.text.toString()
            val valor = binding.editValorMeta.text.toString().toDoubleOrNull() ?: 0.0
            val data = binding.editDataMeta.text.toString()
            val descricao = binding.editDescricao.text.toString()
            val fixada = binding.toggleFixarMeta.isChecked

            if (MetasValidator.validarTodos(nome, valor, data, descricao)) {
                val metaAtualizada = MetasDTO(
                    nome = nome,
                    valor = valor,
                    data = data,
                    descricao = descricao,
                    imagemUri = imagemSelecionadaUri,
                    fixada = fixada
                )

                val intent = Intent(this, DetalheMetaActivity::class.java)
                intent.putExtra("meta", metaAtualizada)
                intent.putExtra("valorAtual", valorAtual)
                startActivity(intent)

                Toast.makeText(this, "Meta atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                finish()

            } else {
                if (!MetasValidator.validarNome(nome)) binding.editNomeMeta.error = "Informe o nome"
                if (!MetasValidator.validarValor(valor)) binding.editValorMeta.error = "Valor deve ser maior que 0"
                if (!MetasValidator.validarData(data)) binding.editDataMeta.error = "Informe a data"
                if (!MetasValidator.validarDescricao(descricao)) binding.editDescricao.error = "Informe a descrição"
                Toast.makeText(this, "Verifique os campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnExcluirMeta.setOnClickListener {
            val intent = Intent(this, MetasActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Meta excluída com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun preencherDados() {
        binding.editNomeMeta.setText(metaRecebida.nome)
        binding.editValorMeta.setText(metaRecebida.valor.toString())
        binding.editDataMeta.setText(metaRecebida.data)
        binding.editDescricao.setText(metaRecebida.descricao)
        binding.toggleFixarMeta.isChecked = metaRecebida.fixada

        metaRecebida.imagemUri?.let {
            imagemSelecionadaUri = it
            binding.imgMeta.setImageURI(it)
        } ?: binding.imgMeta.setImageResource(R.drawable.image_placeholder)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            imagemSelecionadaUri = data?.data
            binding.imgMeta.setImageURI(imagemSelecionadaUri)
        }
    }
}
