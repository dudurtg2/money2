package com.tcc.money.data.models

import com.tcc.money.utils.enums.Genero
import java.time.LocalDateTime

data class HomeModelEntity (
    var nome: String,
    var data: LocalDateTime,
    var genero: Genero
)

