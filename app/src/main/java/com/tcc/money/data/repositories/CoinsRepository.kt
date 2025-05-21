package com.tcc.money.data.repositories

import android.content.Context
import android.util.Log
import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.network.retrofit.CoinsRetrofit
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.UUID


class CoinsRepository(context: Context): ICoinsRepository {

    private val api = CoinsRetrofit.create(context)


    override fun save(coins: Coins): Coins {
        Log.d("APImoney", "save() called with: $coins")

        val response = api.save(coins).execute()
        Log.d("APImoney", "API response code: ${response.code()}, message: ${response.message()}")

        if (response.isSuccessful) {
            val body = response.body()?.use { it.string() }
            if (body.isNullOrBlank()) {
                Log.e("APImoney", "Response body was null or empty")
                throw IOException("Resposta vazia ao salvar Coins")
            }
            Log.d("APImoney", "Raw JSON from server: $body")

            val jsonObject = JSONObject(body)
            val mapped = Coins(
                name   = jsonObject.getString("name"),
                symbol = jsonObject.getString("symbol"),
                image  = jsonObject.getString("image"),
                uuid   = UUID.fromString(jsonObject.getString("uuid"))
            )
            Log.d("APImoney", "Mapped Coins object: $mapped")
            return mapped

        } else {
            Log.e("APImoney", "Erro ao salvar Coins: HTTP ${response.code()}")
            throw IOException("Erro ao salvar Coins: HTTP ${response.code()}")
        }
    }


    override  fun findByUUID(uuid: UUID): Coins {
        val response = api.findByUUID(uuid).execute()
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

    override  fun findAll(): List<Coins> {
        val response = api.findAll().execute()
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

    override fun delete(uuid: UUID) {
        api.delete(uuid)
    }

    override fun update(uuid: UUID, coins: Coins): Coins {
        val response = api.update(uuid, coins).execute()
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
}