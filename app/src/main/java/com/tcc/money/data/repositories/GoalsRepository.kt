package com.tcc.money.data.repositories

import android.content.Context
import com.tcc.money.data.Intefaces.IGoalsRepository
import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Goals
import com.tcc.money.data.models.Movements
import com.tcc.money.network.retrofit.CoinsRetrofit
import com.tcc.money.network.retrofit.GoalsRetrofit
import com.tcc.money.network.retrofit.MovementsRetrofit
import com.tcc.money.utils.enums.TypeCoins
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class GoalsRepository(context: Context) : IGoalsRepository {
    private val api = GoalsRetrofit.create(context)

    override  fun save(goals: Goals): Goals {
        val response = api.save(goals)
        if (response.isSuccessful) {
            val json = response.body()?.string()
            val jsonObject = JSONObject(json)
            val coinsObject = jsonObject.getJSONObject("coins")
            return Goals(
                goal = jsonObject.getString("goal").toFloat(),
                data = LocalDate.parse(jsonObject.getString("data")),
                description = jsonObject.getString("description"),
                coins = Coins(
                    name = coinsObject.getString("name"),
                    symbol = coinsObject.getString("symbol"),
                    image = coinsObject.getString("image"),
                    uuid =  UUID.fromString(jsonObject.getString("uuid"))
                ),
                uuid =  UUID.fromString(jsonObject.getString("uuid"))
            )
        } else {
            throw Exception("Erro no login: ${response.code()}")
        }
    }

    override  fun findByUUID(uuid: UUID): Goals {
        val response = api.findByUUID(uuid)
        if (response.isSuccessful) {
            val json = response.body()?.string()
            val jsonObject = JSONObject(json)
            val coinsObject = jsonObject.getJSONObject("coins")
            return Goals(
                goal = jsonObject.getString("goal").toFloat(),
                data = LocalDate.parse(jsonObject.getString("data")),
                description = jsonObject.getString("description"),
                coins = Coins(
                    name = coinsObject.getString("name"),
                    symbol = coinsObject.getString("symbol"),
                    image = coinsObject.getString("image"),
                    uuid =  UUID.fromString(jsonObject.getString("uuid"))
                ),
                uuid =  UUID.fromString(jsonObject.getString("uuid"))
            )
        } else {
            throw Exception("Erro no login: ${response.code()}")
        }
    }

    override fun findAll(): List<Goals> {
        val response = api.findAll()

        if (response.isSuccessful) {
            val json = response.body()?.string() ?: throw Exception("Resposta vazia")
            val jsonArray = JSONArray(json)
            val goalsList = mutableListOf<Goals>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val coinsObject = jsonObject.getJSONObject("coins")

                val goals = Goals(
                    goal = jsonObject.getString("goal").toFloat(),
                    data = LocalDate.parse(jsonObject.getString("data")),
                    description = jsonObject.getString("description"),
                    coins = Coins(
                        name = coinsObject.getString("name"),
                        symbol = coinsObject.getString("symbol"),
                        image = coinsObject.getString("image"),
                        uuid =  UUID.fromString(jsonObject.getString("uuid"))
                    ),
                    uuid =  UUID.fromString(jsonObject.getString("uuid"))
                )

                goalsList.add(goals)
            }

            return goalsList
        } else {
            throw Exception("Erro na requisição: ${response.code()}")
        }
    }
}