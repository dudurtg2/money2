package com.tcc.money.ui.screens.editar_meta

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
import com.tcc.money.ui.screens.detalhe_meta.DetalheMetaActivity
import com.tcc.money.ui.screens.metas.MetasActivity

class EditarMetaActivity : AppCompatActivity() {

    private lateinit var imgMeta: ImageView
    private lateinit var editNomeMeta: EditText
    private lateinit var editValorMeta: EditText
    private lateinit var editDataMeta: EditText
    private lateinit var editDescricao: EditText
    private lateinit var toggleFixarMeta: ToggleButton
    private lateinit var btnSalvar: Button
    private lateinit var btnExcluir: Button
    private lateinit var btnSelecionarImagem: ImageView
    private lateinit var btnBack: ImageButton

    private var imagemSelecionadaUri: Uri? = null
    private val REQUEST_IMAGE_PICK = 1010

    private lateinit var metaRecebida: MetasDTO
    private var valorAtual: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_meta)

        // Referências dos IDs
        imgMeta = findViewById(R.id.imgMeta)
        editNomeMeta = findViewById(R.id.editNomeMeta)
        editValorMeta = findViewById(R.id.editValorMeta)
        editDataMeta = findViewById(R.id.editDataMeta)
        editDescricao = findViewById(R.id.editDescricao)
        toggleFixarMeta = findViewById(R.id.toggleFixarMeta)
        btnSalvar = findViewById(R.id.btnSalvarMeta)
        btnExcluir = findViewById(R.id.btnExcluirMeta)
        btnSelecionarImagem = findViewById(R.id.btnSelecionarImagem)
        btnBack = findViewById(R.id.btnBack)

        // Recebe dados da tela anterior
        metaRecebida = intent.getSerializableExtra("meta") as MetasDTO
        valorAtual = intent.getDoubleExtra("valorAtual", 0.0)

        preencherDados()

        // Botão Voltar
        btnBack.setOnClickListener {
            finish()
        }

        // Selecionar imagem
        btnSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        // Botão Salvar
        btnSalvar.setOnClickListener {
            val nome = editNomeMeta.text.toString()
            val valor = editValorMeta.text.toString().toDoubleOrNull() ?: 0.0
            val data = editDataMeta.text.toString()
            val descricao = editDescricao.text.toString()
            val fixada = toggleFixarMeta.isChecked

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
                if (!MetasValidator.validarNome(nome)) editNomeMeta.error = "Informe o nome"
                if (!MetasValidator.validarValor(valor)) editValorMeta.error = "Valor deve ser maior que 0"
                if (!MetasValidator.validarData(data)) editDataMeta.error = "Informe a data"
                if (!MetasValidator.validarDescricao(descricao)) editDescricao.error = "Informe a descrição"
                Toast.makeText(this, "Verifique os campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Botão Excluir
        btnExcluir.setOnClickListener {
            val intent = Intent(this, MetasActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Meta excluída com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun preencherDados() {
        editNomeMeta.setText(metaRecebida.nome)
        editValorMeta.setText(metaRecebida.valor.toString())
        editDataMeta.setText(metaRecebida.data)
        editDescricao.setText(metaRecebida.descricao)
        toggleFixarMeta.isChecked = metaRecebida.fixada

        metaRecebida.imagemUri?.let {
            imagemSelecionadaUri = it
            imgMeta.setImageURI(it)
        } ?: imgMeta.setImageResource(R.drawable.image_placeholder)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            imagemSelecionadaUri = data?.data
            imgMeta.setImageURI(imagemSelecionadaUri)
        }
    }
}
