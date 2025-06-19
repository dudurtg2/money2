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

    private var imagemSelecionadaUri: Uri? = null
    private val REQUEST_IMAGE_PICK = 1010

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_meta)

        // Referências
        imgMeta = findViewById(R.id.imgMeta)
        editNomeMeta = findViewById(R.id.editNomeMeta)
        editValorMeta = findViewById(R.id.editValorMeta)
        editDataMeta = findViewById(R.id.editDataMeta)
        editDescricao = findViewById(R.id.editDescricao)
        toggleFixarMeta = findViewById(R.id.toggleFixarMeta)
        btnSalvar = findViewById(R.id.btnSalvarMeta)
        btnExcluir = findViewById(R.id.btnExcluirMeta)
        btnSelecionarImagem = findViewById(R.id.btnSelecionarImagem)

        // Voltar
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Recebe os dados via intent
        val nomeMeta = intent.getStringExtra("nomeMeta") ?: ""
        val valorAtual = intent.getDoubleExtra("valorAtual", 0.0)
        val valorTotal = intent.getDoubleExtra("valorTotal", 0.0)
        val dataLimite = intent.getStringExtra("dataLimite") ?: ""
        val descricao = intent.getStringExtra("descricao") ?: ""
        val imagemUri = intent.getStringExtra("imagemUri")
        val isFixada = intent.getBooleanExtra("isFixada", false)

        // Preenche os campos
        editNomeMeta.setText(nomeMeta)
        editValorMeta.setText(valorTotal.toString())
        editDataMeta.setText(dataLimite)
        editDescricao.setText(descricao)
        toggleFixarMeta.isChecked = isFixada

        // Carrega imagem se tiver URI
        imagemUri?.let {
            val uri = Uri.parse(it)
            imagemSelecionadaUri = uri
            imgMeta.setImageURI(uri)
        }

        // Trocar imagem
        btnSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        // Salvar alterações
        btnSalvar.setOnClickListener {
            val nome = editNomeMeta.text.toString()
            val valor = editValorMeta.text.toString().toDoubleOrNull() ?: 0.0
            val data = editDataMeta.text.toString()
            val descricaoTxt = editDescricao.text.toString()
            val fixar = toggleFixarMeta.isChecked

            // Aqui você salva no banco local ou Firebase
            Toast.makeText(this, "Meta salva com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Excluir meta
        btnExcluir.setOnClickListener {
            // Aqui você chama lógica de exclusão (confirmação opcional)
            Toast.makeText(this, "Meta excluída com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            imagemSelecionadaUri = data.data
            imgMeta.setImageURI(imagemSelecionadaUri)
        }
    }
}
