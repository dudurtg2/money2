package com.tcc.money.data.models

import java.time.LocalDate
import java.util.UUID

data class Goals(
   var uuid: UUID,
   var goal: Float,
   var description: String,
   var coins: Coins,
   var data: LocalDate

)
