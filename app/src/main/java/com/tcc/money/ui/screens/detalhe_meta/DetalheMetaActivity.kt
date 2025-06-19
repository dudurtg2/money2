class DetalheMetaActivity : AppCompatActivity() {

    // Simulando os dados da meta (depois vão vir do banco ou Firebase)
    private val nomeMeta = "Primeiro carro"
    private val valorAtual = 300.0
    private val valorTotal = 1000.0
    private val dataLimite = "15/05/2026"
    private val descricao = "Juntando dinheiro pra comprar meu chevettinho :)"
    private val imagemUri = "content://com.example.app/imagem_meta_1" // Simulando uma URI
    private val iconeResId = R.drawable.ic_car
    private val isFixada = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_meta)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnTransferir = findViewById<Button>(R.id.btnTransferir)
        val btnEditar = findViewById<Button>(R.id.btnEditar)
        val progresso = findViewById<ProgressBar>(R.id.progressoMeta)
        val txtFixada = findViewById<TextView>(R.id.metaFixadaTag)

        // Calcula e aplica o progresso
        val progressoCalculado = (valorAtual / valorTotal * 100).toInt()
        progresso.progress = progressoCalculado

        // Exibe a tag "Meta fixada" se for o caso
        txtFixada.visibility = if (isFixada) View.VISIBLE else View.GONE

        // Ações
        btnBack.setOnClickListener { finish() }

        btnTransferir.setOnClickListener {
            val intent = Intent(this, TransferenciaActivity::class.java)
            intent.putExtra("nomeMeta", nomeMeta)
            intent.putExtra("valorAtual", valorAtual)
            intent.putExtra("valorTotal", valorTotal)
            startActivity(intent)
        }

        btnEditar.setOnClickListener {
            val intent = Intent(this, EditarMetaActivity::class.java)
            intent.putExtra("nomeMeta", nomeMeta)
            intent.putExtra("valorAtual", valorAtual)
            intent.putExtra("valorTotal", valorTotal)
            intent.putExtra("dataLimite", dataLimite)
            intent.putExtra("descricao", descricao)
            intent.putExtra("imagemUri", imagemUri)
            intent.putExtra("iconeResId", iconeResId)
            intent.putExtra("isFixada", isFixada)
            startActivity(intent)
        }
    }
}
