package com.tcc.money.ui.screens.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.applications.find.FindCoinsUseCase
import com.tcc.money.data.applications.LoginUseCase
import com.tcc.money.databinding.ActivityMainBinding
import com.tcc.money.data.models.Coins
import com.tcc.money.data.applications.save.SaveCoinsUseCase
import com.tcc.money.data.dto.Login
import com.tcc.money.data.models.Users
import com.tcc.money.data.repositories.CoinsRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.mapper.CoinsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private val coinsRepository by lazy { CoinsRepository(this) }
    private val coinsDao        by lazy { DataBase.getDatabase(this).coinsDao() }
    private val checkPremium    by lazy { CheckPremiumAccountUseCase(this) }
    private val coinsMapper     by lazy { CoinsMapper() }

    // 2) Injeta tudo no construtor do use case
    private val saveCoinsUseCase by lazy {
        SaveCoinsUseCase(
            repository     = coinsRepository,
            dao            = coinsDao,
            checkPremium   = checkPremium,
            mapper         = coinsMapper
        )
    }

    // Mesma coisa para FindCoinsUseCase e LoginUseCase:
    private val findCoinsUseCase by lazy {
        FindCoinsUseCase(
            repository   = CoinsRepository(this),
            dao          = coinsDao,
            checkPremium = checkPremium,
            mapper       = coinsMapper,
            context = this
        )
    }
    private val loginUseCase by lazy {
        LoginUseCase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()

        // 4) opcional: carrega todas as moedas ao abrir
        loadAllCoins()
    }

    private fun setupListeners() {
        binding.buttonClearDb.setOnClickListener {
            lifecycleScope.launch {
                // 1) Limpa todas as tabelas em background
                withContext(Dispatchers.IO) {
                    DataBase.getDatabase(this@HomeActivity)
                        .clearAllTables()
                }
                // 2) Notifica o usuário na thread de UI
                Toast.makeText(
                    this@HomeActivity,
                    "Banco de dados zerado!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        // salvar uma moeda de teste
        binding.buttonSave.setOnClickListener {
            lifecycleScope.launch {
                runCatching {
                    val newCoin = createTestCoins()
                    saveCoinsUseCase.execute(newCoin)
                }.onSuccess { saved ->
                    binding.tvResult.text = "Salvo: $saved"
                }.onFailure { e ->
                    Log.e("APImoney", "Erro ao salvar: ${e.message}")
                    Toast.makeText(this@HomeActivity, "Erro ao salvar: ${e.message}", LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonLoadAll.setOnClickListener {
            loadAllCoins()
        }

        binding.buttonLoadOne.setOnClickListener {
            lifecycleScope.launch {
                runCatching {
                    val uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
                    findCoinsUseCase.executeById(uuid)
                }.onSuccess { coin ->
                    binding.tvResult.text = "Encontrado: $coin"
                }.onFailure { e ->
                    Toast.makeText(this@HomeActivity, "Erro ao buscar: ${e.message}", LENGTH_SHORT).show()
                }
            }
        }

        // login de usuário
        binding.buttonLogin.setOnClickListener {
            lifecycleScope.launch {
                runCatching { login() }
                    .onSuccess { user ->
                        binding.tvUser.text = "Usuário: $user"
                    }
                    .onFailure { e ->
                        Log.e("LoginUseCase", "Erro no login: ${e.message}")
                        Toast.makeText(this@HomeActivity, "Erro no login: ${e.message}", LENGTH_SHORT).show()
                    }
            }
        }
    }

    // função de apoio para carregar e mostrar todas as moedas
    private fun loadAllCoins() {
        lifecycleScope.launch {
            runCatching { findCoinsUseCase.executeAll() }
                .onSuccess { list ->
                    binding.tvResult.text = list.joinToString("\n")
                }
                .onFailure { e ->
                    Toast.makeText(this@HomeActivity, "Erro ao listar: ${e.message}", LENGTH_SHORT).show()
                }
        }
    }

    // cria uma moeda de teste (você já tinha)
    private suspend fun createTestCoins(): Coins {
        return Coins(
                name = "BTC",
                uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                image = "a",
                symbol = "BTC"
            )

    }

    // login de teste (você já tinha)
    private suspend fun login(): Users {
        return loginUseCase.execute(
            Login("ana.souza2@example.com", "senhaSegura123")
        )
    }
}

