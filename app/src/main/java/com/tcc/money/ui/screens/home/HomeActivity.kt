package com.tcc.money.ui.screens.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.money.R
import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.applications.find.FindCoinsUseCase
import com.tcc.money.data.applications.LoginUseCase
import com.tcc.money.data.applications.save.SaveCoinsUseCase
import com.tcc.money.data.applications.save.SaveMovementsUseCase
import com.tcc.money.data.models.Banco
import com.tcc.money.data.models.Movements
import com.tcc.money.databinding.ActivityMainBinding
import com.tcc.money.ui.screens.home.adapter.BancoAdapter
import com.tcc.money.ui.screens.login.LoginActivity
import com.tcc.money.utils.enums.TypeCoins
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var checkPremium: CheckPremiumAccountUseCase

    @Inject
    lateinit var saveMovementsUseCase: SaveMovementsUseCase

    @Inject
    lateinit var saveCoinsUseCase: SaveCoinsUseCase

    @Inject
    lateinit var findCoinsUseCase: FindCoinsUseCase

    @Inject
    lateinit var loginUseCase: LoginUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupBancoRecyclerView()
        loadAllCoins()
    }

    private fun setupListeners() {
        binding.buttonClearDb.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    com.tcc.money.database.DataBase
                        .getDatabase(this@HomeActivity)
                        .clearAllTables()
                }
                Toast.makeText(
                    this@HomeActivity,
                    "Banco de dados zerado!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.buttonSave.setOnClickListener {
            lifecycleScope.launch {
                runCatching {
                    val newMovement = createTestMovements(createTestCoins())
                    saveMovementsUseCase.execute(newMovement)
                }.onSuccess { movement ->
                    binding.tvResult.text = "Movimento salvo:\n$movement"
                    Toast.makeText(
                        this@HomeActivity,
                        "Movimento salvo com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                }.onFailure { e ->
                    Log.e("HomeActivity", "Erro ao salvar movimento: ${e.message}", e)
                    binding.tvResult.text = "Falha ao salvar movimento: ${e.message}"
                    Toast.makeText(
                        this@HomeActivity,
                        "Erro: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
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
                    Toast.makeText(
                        this@HomeActivity,
                        "Erro ao buscar: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.buttonLogin.setOnClickListener {
            lifecycleScope.launch {
                runCatching { login() }
                    .onSuccess { user ->
                        binding.tvUser.text = "Usuário: $user"
                    }
                    .onFailure { e ->
                        Log.e("LoginUseCase", "Erro no login: ${e.message}")
                        Toast.makeText(
                            this@HomeActivity,
                            "Erro no login: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

    private fun setupBancoRecyclerView() {
        lifecycleScope.launch {
            val listaBancos = withContext(Dispatchers.IO) {
                com.tcc.money.database.DataBase
                    .getDatabase(this@HomeActivity)
                    .bancoDao()
                    .getAllBancos()
            }

            val bancoAdapter = BancoAdapter(listaBancos)

            binding.rvBancos.apply {
                adapter = bancoAdapter
                layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }
    }

    private fun loadAllCoins() {
        lifecycleScope.launch {
            runCatching { findCoinsUseCase.executeAll() }
                .onSuccess { list ->
                    binding.tvResult.text = list.joinToString("\n")
                }
                .onFailure { e ->
                    Toast.makeText(
                        this@HomeActivity,
                        "Erro ao listar: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private suspend fun createTestCoins() = com.tcc.money.data.models.Coins(
        name = "BTC",
        uuid = UUID.fromString("ce051108-5715-42f1-b17b-3954a2ae9721"),
        image = "a",
        symbol = "BTC"
    )

    private suspend fun createTestMovements(coins: com.tcc.money.data.models.Coins) = Movements(
        typeCoins = TypeCoins.CRIPTO,
        coins = coins,
        date = LocalDateTime.now(),
        price = 123.12f,
        uuid = UUID.randomUUID(),
        value = 10.1f
    )

    private suspend fun login() {
        loginUseCase.logouf()
        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        finish()
    }
}
