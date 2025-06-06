package com.tcc.money.data.repositories

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.network.api.CoinsApi
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinsRepository @Inject constructor(
    private val api: CoinsApi
) : ICoinsRepository {

    override fun save(coins: Coins): Coins {
        val response = api.save(coins).execute()
        if (response.isSuccessful) {
            val body = response.body()?.use { it.string() }
            if (body.isNullOrBlank()) {
                throw IOException("Resposta vazia ao salvar Coins")
            }
            val jsonObject = JSONObject(body)
            return Coins(
                name   = jsonObject.getString("name"),
                symbol = jsonObject.getString("symbol"),
                image  = jsonObject.getString("image"),
                uuid   = UUID.fromString(jsonObject.getString("uuid"))
            )
        } else {
            throw IOException("Erro ao salvar Coins: HTTP ${response.code()}")
        }
    }

    override fun findByUUID(uuid: UUID): Coins {
        val response = api.findByUUID(uuid).execute()
        if (response.isSuccessful) {
            val json = response.body()?.string() ?: throw IOException("Body nulo")
            val jsonObject = JSONObject(json)
            return Coins(
                name   = jsonObject.getString("name"),
                symbol = jsonObject.getString("symbol"),
                image  = jsonObject.getString("image"),
                uuid   = UUID.fromString(jsonObject.getString("uuid"))
            )
        } else {
            throw IOException("Erro ao buscar por UUID: HTTP ${response.code()}")
        }
    }

    override fun findAll(): List<Coins> {
        val response = api.findAll().execute()
        if (response.isSuccessful) {
            val json = response.body()?.string() ?: throw IOException("Body nulo")
            val jsonArray = JSONArray(json)
            val coinsList = mutableListOf<Coins>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val coin = Coins(
                    name   = obj.getString("name"),
                    symbol = obj.getString("symbol"),
                    image  = obj.getString("image"),
                    uuid   = UUID.fromString(obj.getString("uuid"))
                )
                coinsList.add(coin)
            }
            return coinsList
        } else {
            throw IOException("Erro na requisição findAll: HTTP ${response.code()}")
        }
    }

    override fun delete(uuid: UUID) {
        api.delete(uuid)
    }

    override fun update(uuid: UUID, coins: Coins): Coins {
        val response = api.update(uuid, coins).execute()
        if (response.isSuccessful) {
            val json = response.body()?.string() ?: throw IOException("Body nulo")
            val jsonObject = JSONObject(json)
            return Coins(
                name   = jsonObject.getString("name"),
                symbol = jsonObject.getString("symbol"),
                image  = jsonObject.getString("image"),
                uuid   = UUID.fromString(jsonObject.getString("uuid"))
            )
        } else {
            throw IOException("Erro no update: HTTP ${response.code()}")
        }
    }
}
