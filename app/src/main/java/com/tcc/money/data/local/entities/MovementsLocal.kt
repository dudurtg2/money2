package com.tcc.money.data.local.entities

import com.tcc.money.utils.enums.TypeCoins
import java.time.LocalDateTime

data class MovementsLocal(
    val date: LocalDateTime,
    val value: Float,
    val price: Float,
    val coins: CoinsLocal,
    val typeCoins: TypeCoins
)
