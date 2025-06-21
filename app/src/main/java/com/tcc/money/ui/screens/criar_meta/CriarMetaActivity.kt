package com.tcc.money.ui.screens.criar_meta

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.data.dto.MetasDTO
import com.tcc.money.utils.validator.MetasValidator
import com.tcc.money.ui.screens.metas.MetasActivity
import android.widget.Toast
import com.tcc.money.databinding.ActivityCriarMetaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CriarMetaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCriarMetaBinding

    private var imagemSelecionada: Uri? = null
    private val REQUEST_IMAGE_PICK = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarMetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        binding.btnAdicionarMeta.setOnClickListener {
            val nome = binding.editNomeMeta.text.toString()
            val valor = binding.editValorMeta.text.toString().toDoubleOrNull() ?: 0.0
            val data = binding.editDataMeta.text.toString()
            val descricao = binding.editDescricao.text.toString()
            val fixar = binding.toggleFixarMeta.isChecked

            if (MetasValidator.validarTodos(nome, valor, data, descricao)) {
                val meta = MetasDTO(
                    nome = nome,
                    valor = valor,
                    data = data,
                    descricao = descricao,
                    imagemUri = imagemSelecionada,
                    fixada = fixar
                )

                val resultIntent = Intent(this, MetasActivity::class.java)
                resultIntent.putExtra("meta", meta)
                startActivity(resultIntent)

                Toast.makeText(this, "Meta adicionada com sucesso!", Toast.LENGTH_SHORT).show()
                finish()

            } else {
                if (!MetasValidator.validarNome(nome)) binding.editNomeMeta.error = "Informe o nome"
                if (!MetasValidator.validarValor(valor)) binding.editValorMeta.error = "Valor deve ser maior que 0"
                if (!MetasValidator.validarData(data)) binding.editDataMeta.error = "Informe a data"
                if (!MetasValidator.validarDescricao(descricao)) binding.editDescricao.error = "Informe a descrição"
                Toast.makeText(this, "Verifique os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            imagemSelecionada = data.data
            binding.imgMeta.setImageURI(imagemSelecionada)
        }
    }
}
