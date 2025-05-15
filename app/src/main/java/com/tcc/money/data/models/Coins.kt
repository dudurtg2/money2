package com.tcc.money.data.models

import java.util.UUID

data class Coins(
   val uuid: UUID,
   val name: String,
   val symbol: String,
   val image: String
)
