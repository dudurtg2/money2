package com.tcc.money.data.dto

import android.net.Uri
import java.io.Serializable

data class MetasDTO(
    val nome: String,
    val valor: Double,
    val data: String,
    val descricao: String,
    val imagemUri: Uri? = null,
    val fixada: Boolean = false
) : Serializable
