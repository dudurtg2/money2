package com.tcc.money.ui.screens.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tcc.money.data.applications.find.FindCoinsUseCase
import com.tcc.money.data.applications.LoginUseCase
import com.tcc.money.databinding.ActivityMainBinding
import com.tcc.money.data.models.Coins
import com.tcc.money.data.applications.save.SaveCoinsUseCase
import com.tcc.money.data.dto.Login
import com.tcc.money.data.models.Users
import kotlinx.coroutines.launch
import java.util.UUID

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var saveCoinsUseCase: SaveCoinsUseCase
    private lateinit var findCoinsUseCase: FindCoinsUseCase
    private lateinit var loginUseCase: LoginUseCase

    private fun initalazy() {
        saveCoinsUseCase = SaveCoinsUseCase(this)
        findCoinsUseCase = FindCoinsUseCase(this)
        loginUseCase = LoginUseCase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initalazy()

        binding.button.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val coins = createTestCoins()
                    val saved = saveCoinsUseCase.execute(coins)
                    binding.oi.text = saved.toString()
                } catch (e: Exception) {
                    Log.e("HomeActivity", "Erro ao salvar moedas", e)
                    Toast.makeText(
                        this@HomeActivity,
                        e.message ?: "Erro desconhecido",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.button2.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val users = login()
                    binding.textView.text = users.toString()
                } catch (e: Exception) {
                }
            }
        }
    }

    private suspend fun login(): Users {
        val login = Login(
            login = "ana.souza2@example.com",
            senha = "senhaSegura123"
        )
        return loginUseCase.execute(login)
    }

    private suspend fun createTestCoins(): Coins {

        return saveCoinsUseCase.execute(
            Coins(
                name = "BTC",
                uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                image = "a",
                symbol = "BTC"
            )
        )
    }
}
