package com.tcc.money.ui.screens.criar_meta

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.R
import com.tcc.money.data.dto.MetasDTO
import com.tcc.money.utils.validator.MetasValidator
import com.tcc.money.ui.screens.metas.MetasActivity

class CriarMetaActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnAdicionar: Button
    private lateinit var toggleFixarMeta: ToggleButton
    private lateinit var imgMeta: ImageView
    private lateinit var editNomeMeta: EditText
    private lateinit var editValorMeta: EditText
    private lateinit var editDataMeta: EditText
    private lateinit var editDescricao: EditText
    private lateinit var btnSelecionarImagem: ImageView

    private var imagemSelecionada: Uri? = null
    private val REQUEST_IMAGE_PICK = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_meta)

        // IDs
        btnBack = findViewById(R.id.btnBack)
        btnAdicionar = findViewById(R.id.btnAdicionarMeta)
        toggleFixarMeta = findViewById(R.id.toggleFixarMeta)
        imgMeta = findViewById(R.id.imgMeta)
        editNomeMeta = findViewById(R.id.editNomeMeta)
        editValorMeta = findViewById(R.id.editValorMeta)
        editDataMeta = findViewById(R.id.editDataMeta)
        editDescricao = findViewById(R.id.editDescricao)
        btnSelecionarImagem = findViewById(R.id.btnSelecionarImagem)

        // Voltar
        btnBack.setOnClickListener {
            finish()
        }

        // Selecionar imagem
        btnSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        // Adicionar meta
        btnAdicionar.setOnClickListener {
            val nome = editNomeMeta.text.toString()
            val valor = editValorMeta.text.toString().toDoubleOrNull() ?: 0.0
            val data = editDataMeta.text.toString()
            val descricao = editDescricao.text.toString()
            val fixar = toggleFixarMeta.isChecked

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
                if (!MetasValidator.validarNome(nome)) editNomeMeta.error = "Informe o nome"
                if (!MetasValidator.validarValor(valor)) editValorMeta.error = "Valor deve ser maior que 0"
                if (!MetasValidator.validarData(data)) editDataMeta.error = "Informe a data"
                if (!MetasValidator.validarDescricao(descricao)) editDescricao.error = "Informe a descrição"
                Toast.makeText(this, "Verifique os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Receber imagem selecionada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            imagemSelecionada = data.data
            imgMeta.setImageURI(imagemSelecionada)
        }
    }
}
