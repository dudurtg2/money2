package com.tcc.money.data.repositories

import android.util.Log
import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Movements
import com.tcc.money.network.api.MovementsApi
import com.tcc.money.utils.enums.TypeCoins
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class MovementsRepository@Inject constructor(
    private val api: MovementsApi
) : IMovementsRepository {

    private val TAG = "MovementsRepo"
    override fun save(movements: Movements): Movements {
        Log.d(TAG, "save() → enviando movimento: $movements")
        val response = api.save(movements).execute()
        Log.d(TAG, "save() ← HTTP ${response.code()}")
        if (response.isSuccessful) {
            val json = response.body()?.string().orEmpty()
            Log.d(TAG, "save() ← body: $json")
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
                    uuid = UUID.fromString(coinsObject.getString("uuid"))
                ),
                typeCoins = TypeCoins.valueOf(jsonObject.getString("typeCoins")),
                uuid = UUID.fromString(jsonObject.getString("uuid"))
            )
        } else {
            Log.e(TAG, "save() Erro HTTP: ${response.code()} - ${response.errorBody()?.string()}")
            throw Exception("Erro ao salvar movimento: ${response.code()}")
        }
    }

    override  fun findByUUID(uuid: UUID): Movements {
        val response = api.findByUUID(uuid).execute()
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

    override  fun findAll(): List<Movements> {
        val response = api.findAll().execute()

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

    override fun delete(uuid: UUID) {
        api.delete(uuid)
    }

    override fun update(uuid: UUID, movements: Movements): Movements {
        val response = api.update(uuid, movements).execute()
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
}