package com.tcc.money.data.models

import com.tcc.money.utils.enums.TypeCoins
import java.time.LocalDateTime
import java.util.UUID

data class Movements(
    val uuid: UUID,
    val date: LocalDateTime,
    val value: Float,
    val price: Float,
    val coins: Coins,
    val typeCoins: TypeCoins
)
