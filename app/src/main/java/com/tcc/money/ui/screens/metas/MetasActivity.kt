class MetasActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnNovaMeta: Button
    private lateinit var progressMetaPrincipal: ProgressBar
    private lateinit var progressOutraMeta: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metas)

        // IDs
        btnBack = findViewById(R.id.btnBack)
        btnNovaMeta = findViewById(R.id.btnNovaMeta)
        progressMetaPrincipal = findViewById(R.id.progressMetaPrincipal)
        progressOutraMeta = findViewById(R.id.progressOutraMeta)

        // Voltar
        btnBack.setOnClickListener {
            finish()
        }

        // Nova meta
        btnNovaMeta.setOnClickListener {
            val intent = Intent(this, CriarMetaActivity::class.java)
            startActivity(intent)
        }

        // Exemplo de progresso
        val arrecadadoPrincipal = 300.0
        val totalPrincipal = 1000.0
        val progressoPrincipal = (arrecadadoPrincipal / totalPrincipal * 100).toInt()
        progressMetaPrincipal.progress = progressoPrincipal

        val arrecadadoOutra = 0.0
        val totalOutra = 500.0
        val progressoOutra = (arrecadadoOutra / totalOutra * 100).toInt()
        progressOutraMeta.progress = progressoOutra
    }
}
