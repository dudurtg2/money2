package com.tcc.money.data.repositories

import android.content.Context
import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Movements
import com.tcc.money.network.retrofit.CoinsRetrofit
import com.tcc.money.network.retrofit.MovementsRetrofit
import com.tcc.money.utils.enums.TypeCoins
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.UUID

class MovementsRepository(context: Context) : IMovementsRepository {
    private val api = MovementsRetrofit.create(context)

    override suspend fun save(movements: Movements): Movements {
        val response = api.save(movements)
        if (response.isSuccessful) {
            val json = response.body()?.string()
            val jsonObject = JSONObject(json)
            val coinsObject = jsonObject.getJSONObject("coins")
            return Movements(
                date = LocalDateTime.parse(jsonObject.getString("date")),
                value = jsonObject.getString("value").toFloat(),
                price = jsonObject.getString("price").toFloat(),
                coins = Coins(
                    name = coinsObject.getString("name"),
                    symbol = coinsObject.getString("symbol"),
                    image = coinsObject.getString("image"),
                    uuid =  UUID.fromString(jsonObject.getString("uuid"))
                ),
                typeCoins = TypeCoins.valueOf(jsonObject.getString("typeCoins")),
                uuid =  UUID.fromString(jsonObject.getString("uuid"))
            )
        } else {
            throw Exception("Erro no login: ${response.code()}")
        }
    }

    override suspend fun findByUUID(uuid: UUID): Movements {
        val response = api.findByUUID(uuid)
        if (response.isSuccessful) {
            val json = response.body()?.string()
            val jsonObject = JSONObject(json)
            val coinsObject = jsonObject.getJSONObject("coins")
            return Movements(
                date = LocalDateTime.parse(jsonObject.getString("date")),
                value = jsonObject.getString("value").toFloat(),
                price = jsonObject.getString("price").toFloat(),
                coins = Coins(
                    name = coinsObject.getString("name"),
                    symbol = coinsObject.getString("symbol"),
                    image = coinsObject.getString("image"),
                    uuid =  UUID.fromString(jsonObject.getString("uuid"))
                ),
                typeCoins = TypeCoins.valueOf(jsonObject.getString("typeCoins")),
                uuid =  UUID.fromString(jsonObject.getString("uuid"))
            )
        } else {
            throw Exception("Erro no login: ${response.code()}")
        }
    }

    override suspend fun findAll(): List<Movements> {
        val response = api.findAll()

        if (response.isSuccessful) {
            val json = response.body()?.string() ?: throw Exception("Resposta vazia")
            val jsonArray = JSONArray(json)
            val movementsList = mutableListOf<Movements>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val coinsObject = jsonObject.getJSONObject("coins")

                val movement = Movements(
                    date = LocalDateTime.parse(jsonObject.getString("date")),
                    value = jsonObject.getString("value").toFloat(),
                    price = jsonObject.getString("price").toFloat(),
                    coins = Coins(
                        name = coinsObject.getString("name"),
                        symbol = coinsObject.getString("symbol"),
                        image = coinsObject.getString("image"),
                        uuid =  UUID.fromString(jsonObject.getString("uuid"))
                    ),
                    typeCoins = TypeCoins.valueOf(jsonObject.getString("typeCoins")),
                    uuid =  UUID.fromString(jsonObject.getString("uuid"))
                )

                movementsList.add(movement)
            }

            return movementsList
        } else {
            throw Exception("Erro na requisição: ${response.code()}")
        }
    }
}