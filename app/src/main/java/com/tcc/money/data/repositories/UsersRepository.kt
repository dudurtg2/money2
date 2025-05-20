package com.tcc.money.data.repositories

import android.content.Context
import com.tcc.money.data.Intefaces.IUsersRepository
import com.tcc.money.data.dto.Login
import com.tcc.money.data.models.Users
import com.tcc.money.data.services.AuthenticateService
import com.tcc.money.network.retrofit.UsersRetrofit
import com.tcc.money.utils.enums.TypeAccount
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.UUID
import android.util.Log

class UsersRepository(context: Context) : IUsersRepository {
    private val api = UsersRetrofit.create(context)
    private val contexts = context

    override fun refreshToken() {

        val response = api.refreshToken().execute()

        if (response.isSuccessful) {
            val json = response.body()?.string()
            Log.d("UsersRepository", "Resposta da API: $json")

            val jsonObject = JSONObject(json)
            val accessToken = jsonObject.getString("accessToken")
            AuthenticateService.saveToken(contexts, accessToken)

        } else {
            Log.e("UsersRepository", "Erro no login: código ${response.code()}")
            throw Exception("Erro no login: ${response.code()}")
        }
    }

    override fun check(): TypeAccount {
        val response = api.check()

        if (response.isSuccessful) {
            val json = response.body()?.string()
            Log.d("UsersRepository", "Resposta da API: $json")

            val jsonObject = JSONObject(json)
            val role = TypeAccount.valueOf(jsonObject.getString("role"))

            return role
        }
        else {
            Log.e("UsersRepository", "Erro no login: código ${response.code()}")
            throw Exception("Erro no login: ${response.code()}")
        }
    }

    override fun login(login: Login): Users {
        Log.d("UsersRepository", "Iniciando login para: ${login.login}")
        val response = api.login(login).execute()

        if (response.isSuccessful) {
            val json = response.body()?.string()
            Log.d("UsersRepository", "Resposta da API: $json")

            val jsonObject = JSONObject(json)
            val accessToken = jsonObject.getString("accessToken")
            val refreshToken = jsonObject.getString("refreshToken")
            val jsonUser = jsonObject.getJSONObject("user")
            AuthenticateService.saveToken(contexts, accessToken)
            AuthenticateService.saveRefreshToken(contexts, refreshToken)
            Log.d("UsersRepository", "accessToken salvo: $accessToken")
            Log.d("UsersRepository", "refreshToken salvo: $refreshToken")

            try {
                val user = Users(
                    dataNascimento = LocalDateTime.parse(jsonUser.getString("dataNascimento")),
                    nome = jsonUser.getString("name"),
                    genero = jsonUser.getString("genero"),
                    email = jsonUser.getString("email"),
                    cpf = jsonUser.getString("cpf"),
                    telefone = jsonUser.getString("telefone"),
                    type = TypeAccount.valueOf(jsonUser.getString("role")),
                    uuid = UUID.fromString(jsonUser.getString("uuid"))
                )
                Log.d("UsersRepository", "Usuário autenticado: $user")
                return user
            } catch (e: Exception) {
                Log.e("UsersRepository", "Erro ao criar usuário: ${e.message}", e)
                throw Exception("Erro ao processar dados do usuário")
            }

        } else {
            Log.e("UsersRepository", "Erro no login: código ${response.code()}")
            throw Exception("Erro no login: ${response.code()}")
        }
    }

    override fun update(users: Users): Users {
        Log.d("UsersRepository", "Iniciando atualização para o usuário: ${users.uuid}")
        val response = api.update(users).execute()

        if (response.isSuccessful) {
            val json = response.body()?.string()
            Log.d("UsersRepository", "Resposta da API: $json")

            val jsonObject = JSONObject(json).getJSONObject("user")

            val updatedUser = Users(
                nome = jsonObject.getString("name"),
                dataNascimento = LocalDateTime.parse(jsonObject.getString("dataNascimento")),
                genero = jsonObject.getString("genero"),
                email = jsonObject.getString("email"),
                cpf = jsonObject.getString("cpf"),
                telefone = jsonObject.getString("telefone"),
                type = TypeAccount.valueOf(jsonObject.getString("role")),
                uuid = UUID.fromString(jsonObject.getString("uuid"))
            )
            Log.d("UsersRepository", "Usuário atualizado: ${updatedUser.uuid}")
            return updatedUser
        } else {
            Log.e("UsersRepository", "Erro ao atualizar usuário: código ${response.code()}")
            throw Exception("Erro na atualização: ${response.code()}")
        }
    }
}
