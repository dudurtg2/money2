package com.tcc.money.data.repositories

import android.content.Context
import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.network.retrofit.CoinsRetrofit
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID


class CoinsRepository(context: Context): ICoinsRepository {

    private val api = CoinsRetrofit.create(context)

    override suspend fun save(coins: Coins): Coins {
        val response = api.save(coins)
        if (response.isSuccessful) {
            val json = response.body()?.string()
            val jsonObject = JSONObject(json)

            return Coins(
                name =  jsonObject.getString("name"),
                symbol = jsonObject.getString("symbol"),
                image = jsonObject.getString("image"),
                uuid =  UUID.fromString(jsonObject.getString("uuid"))
            )
        } else {
            throw Exception("Erro no login: ${response.code()}")
        }

    }

    override suspend fun findByUUID(uuid: UUID): Coins {
        val response = api.findByUUID(uuid)
        if (response.isSuccessful) {
            val json = response.body()?.string()
            val jsonObject = JSONObject(json)

            return Coins(
                name =  jsonObject.getString("name"),
                symbol = jsonObject.getString("symbol"),
                image = jsonObject.getString("image"),
                uuid =  UUID.fromString(jsonObject.getString("uuid"))
            )
        } else {
            throw Exception("Erro no login: ${response.code()}")
        }

    }

    override suspend fun findAll(): List<Coins> {
        val response = api.findAll()
        if (response.isSuccessful) {
            val json = response.body()?.string()
            val jsonArray = JSONArray(json)

            val coinsList = mutableListOf<Coins>()


            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                val coin = Coins(
                    name = jsonObject.getString("name"),
                    symbol = jsonObject.getString("symbol"),
                    image = jsonObject.getString("image"),
                    uuid =  UUID.fromString(jsonObject.getString("uuid"))
                )

                coinsList.add(coin)
            }

            return coinsList
        } else {
            throw Exception("Erro na requisição: ${response.code()}")
        }

    }
}