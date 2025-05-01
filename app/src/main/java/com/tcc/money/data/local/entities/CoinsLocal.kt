package com.tcc.money.data.local.entities

import com.tcc.money.utils.enums.TypeAccount

data class CoinsLocal(
   val name: String,
   val symbol: String,
   val image: String,
   val user: UsersLocal
)
