package com.tcc.money.data.models

import java.util.UUID

data class Coins(
   var uuid: UUID,
   var name: String,
   var symbol: String,
   var image: String
)
