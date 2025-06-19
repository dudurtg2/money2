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

    private val REQUEST_IMAGE_PICK = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_meta)

        // Inicializa componentes
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

        // Selecionar imagem da galeria
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

            // Aqui você pode salvar no banco ou Firebase
            Toast.makeText(this, "Meta '$nome' adicionada!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // Receber a imagem selecionada da galeria
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data
            if (selectedImageUri != null) {
                imgMeta.setImageURI(selectedImageUri)
            }
        }
    }
}
