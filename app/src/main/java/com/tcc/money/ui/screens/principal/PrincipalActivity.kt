package com.tcc.money.ui.screens.principal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tcc.money.data.dto.Cadastro
import com.tcc.money.databinding.ActivityPrincipalBinding
import com.tcc.money.ui.screens.notification.NotificationsActivity
import com.tcc.money.ui.screens.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recebendo dados do cadastro (se vier)
        val cadastro = intent.getSerializableExtra("cadastro") as? Cadastro
        binding.txtUsuario.text = cadastro?.nome ?: "Usuário"

        // Botão de Entrada
        binding.entryValuesTxt.setOnClickListener {
            Toast.makeText(this, "Entrada clicada", Toast.LENGTH_SHORT).show()
            // Aqui você pode abrir uma tela específica de entrada
        }

        // Botão de Filtro
        binding.entryValuesFilterTxt.setOnClickListener {
            Toast.makeText(this, "Filtro clicado", Toast.LENGTH_SHORT).show()
            // Aqui você pode abrir uma tela ou dialog de filtros
        }

        // Ícone de Perfil
        binding.ivProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Ícone de Notificações
        binding.ivNottification.setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
        }

        // Navbar - Botão Dashboard
        binding.navbar.getChildAt(1).setOnClickListener {
            Toast.makeText(this, "Dashboard clicado", Toast.LENGTH_SHORT).show()
            // Aqui futuramente pode abrir a tela de Dashboard
        }

        // Navbar - Botão Metas
        binding.navbar.getChildAt(2).setOnClickListener {
            Toast.makeText(this, "Metas clicado", Toast.LENGTH_SHORT).show()
            // Aqui futuramente pode abrir a tela de Metas
        }

        // Navbar - Botão Lançar (Central)
        binding.navbar.getChildAt(0).setOnClickListener {
            Toast.makeText(this, "Lançar clicado", Toast.LENGTH_SHORT).show()
            // Aqui futuramente pode abrir a tela de lançamento (adicionar/retirar)
        }
    }
}
