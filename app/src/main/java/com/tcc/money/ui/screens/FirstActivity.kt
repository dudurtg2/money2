package com.tcc.money.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.tcc.money.data.services.IsApiAvailableNowService
import com.tcc.money.data.services.SynchronizationService
import com.tcc.money.databinding.ActivityFirstBinding
import com.tcc.money.ui.screens.home.HomeActivity
import com.tcc.money.ui.screens.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FirstActivity : ComponentActivity() {
    @Inject
    lateinit var isApiAvailableNowService: IsApiAvailableNowService

    private lateinit var binding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkAlreadyAuthenticated()
    }

    private fun checkAlreadyAuthenticated() {
        lifecycleScope.launch {
            val authenticated = isApiAvailableNowService.execute(this@FirstActivity)
            if (authenticated) {
                startActivity(Intent(this@FirstActivity, HomeActivity::class.java))
                finish()
            } else {

                startActivity(Intent(this@FirstActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}
