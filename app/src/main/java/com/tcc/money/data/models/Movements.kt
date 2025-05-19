package com.tcc.money.data.models

import com.tcc.money.utils.enums.TypeCoins
import java.time.LocalDateTime
import java.util.UUID

data class Movements(
    var uuid: UUID,
    var date: LocalDateTime,
    var value: Float,
    var price: Float,
    var coins: Coins,
    var typeCoins: TypeCoins
)
