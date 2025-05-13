package com.tcc.money.data.repositories

import android.content.Context
import com.tcc.money.data.Intefaces.IUsersRepository
import com.tcc.money.data.models.Users
import com.tcc.money.data.services.AuthenticateService
import com.tcc.money.network.retrofit.UsersRetrofit
import com.tcc.money.utils.enums.TypeAccount
import org.json.JSONObject
import java.time.LocalDateTime


class UsersRepository(context: Context): IUsersRepository {
    private val api = UsersRetrofit.create(context)
    private val contexts = context
    override suspend fun login(users: Users): Users {
        val response = api.login(users)
        if (response.isSuccessful) {
            val json = response.body()?.string()
            val jsonObject = JSONObject(json)

            AuthenticateService.saveToken(contexts ,jsonObject.getString("token"));
            return Users(
                nome = jsonObject.getString("nome"),
                dataNascimento = LocalDateTime.parse(jsonObject.getString("dataNascimento")),
                genero = jsonObject.getString("genero"),
                email = jsonObject.getString("email"),
                cpf = jsonObject.getString("cpf"),
                telefone = jsonObject.getString("telefone"),
                type = TypeAccount.valueOf(jsonObject.getString("type"))
            )
        } else {
            throw Exception("Erro no login: ${response.code()}")
        }
    }

    suspend  override fun update(users: Users): Users {
       val response = api.update(users)
        if (response.isSuccessful) {
            val json = response.body()?.string()
            val jsonObject = JSONObject(json)
            return Users(
                nome = jsonObject.getString("nome"),
                dataNascimento = LocalDateTime.parse(jsonObject.getString("dataNascimento")),
                genero = jsonObject.getString("genero"),
                email = jsonObject.getString("email"),
                cpf = jsonObject.getString("cpf"),
                telefone = jsonObject.getString("telefone"),
                type = TypeAccount.valueOf(jsonObject.getString("type"))
            )
        } else {
            throw Exception("Erro no login: ${response.code()}")
        }
    }


}