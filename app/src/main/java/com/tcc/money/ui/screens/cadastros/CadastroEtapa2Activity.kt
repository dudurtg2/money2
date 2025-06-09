package com.tcc.money.ui.screens.cadastros

import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.data.dto.Cadastro
import com.tcc.money.databinding.ActivityCadastroEtapa2Binding

class CadastroEtapa2Activity : AppCompatActivity() {
    private var senhaVisivel = false
    private var confirmarVisivel = false
    private lateinit var binding: ActivityCadastroEtapa2Binding

    private lateinit var dadosRecebidos: Cadastro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroEtapa2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recebe o DTO enviado da Etapa 1
        dadosRecebidos = intent.getSerializableExtra("cadastro") as Cadastro

        // Alternar visibilidade da senha
        binding.ivToggleSenha.setOnClickListener {
            senhaVisivel = !senhaVisivel
            binding.etSenha.inputType = if (senhaVisivel)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.etSenha.setSelection(binding.etSenha.text.length)
        }

        // Alternar visibilidade da confirmação
        binding.ivToggleConfirmar.setOnClickListener {
            confirmarVisivel = !confirmarVisivel
            binding.etConfirmarSenha.inputType = if (confirmarVisivel)
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.etConfirmarSenha.setSelection(binding.etConfirmarSenha.text.length)
        }

        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            finish()
        }

        // Botão Cadastrar
        binding.btnCadastrar.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etSenha.text.toString().trim()
            val confirmar = binding.etConfirmarSenha.text.toString().trim()

            when {
                email.isEmpty() || senha.isEmpty() || confirmar.isEmpty() -> {
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
                senha != confirmar -> {
                    Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Atualiza o DTO com email e senha
                    val cadastroCompleto = dadosRecebidos.copy(
                        email = email,
                        senha = senha
                    )

                    // Aqui você pode salvar o cadastro ou enviar pra API
                    Toast.makeText(this, "Cadastro concluído com sucesso!", Toast.LENGTH_SHORT).show()

                    // Exemplo: log dos dados
                    println("Cadastro completo: $cadastroCompleto")

                    // TODO: Navegar para a Home ou outra tela se quiser
                }
            }
        }
    }
}
