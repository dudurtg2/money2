package com.tcc.money.data.api.models

import com.tcc.money.utils.enums.TypeCoins
import java.time.LocalDateTime

data class Movements(
    val date: LocalDateTime,
    val value: Float,
    val price: Float,
    val coins: Coins,
    val typeCoins: TypeCoins
)
