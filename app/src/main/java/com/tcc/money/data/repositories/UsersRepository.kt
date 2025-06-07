package com.tcc.money.data.repositories

import android.content.Context
import android.util.Log
import com.tcc.money.data.Intefaces.IUsersRepository
import com.tcc.money.data.dto.Login
import com.tcc.money.data.models.Users
import com.tcc.money.data.services.AuthenticateService
import com.tcc.money.network.api.UsersApi
import com.tcc.money.utils.enums.TypeAccount
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val api: UsersApi,
    @ApplicationContext private val context: Context
) : IUsersRepository {

    override fun refreshToken() {
        val response = api.refreshToken().execute()
        if (response.isSuccessful) {
            val json = response.body()?.string().orEmpty()
            Log.d("UsersRepository", "Resposta da API (refreshToken): $json")

            val jsonObject = JSONObject(json)
            val accessToken = jsonObject.getString("accessToken")
            AuthenticateService.saveToken(context, accessToken)
        } else {
            Log.e(
                "UsersRepository",
                "Erro no refreshToken: código ${response.code()}"
            )
            throw Exception("Erro no refreshToken: ${response.code()}")
        }
    }

    override fun check(): TypeAccount {
        val response = api.check().execute()
        Log.d("UsersRepository", "Resposta da API (check): $response")
        if (response.isSuccessful) {
            val json = response.body()?.string().orEmpty()
            Log.d("UsersRepository", "Resposta da API (check): $json")

            val jsonObject = JSONObject(json)
            return TypeAccount.valueOf(jsonObject.getString("role"))
        } else {
            Log.e(
                "UsersRepository",
                "Erro no check(): código ${response.code()}"
            )
            throw Exception("Erro no check(): ${response.code()}")
        }
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun online(): Boolean {
        return api.online().execute().isSuccessful
    }

    override fun login(login: Login): Users {
        Log.d("UsersRepository", "Iniciando login para: ${login.login}")
        val response = api.login(login).execute()

        if (response.isSuccessful) {
            val json = response.body()?.string().orEmpty()
            Log.d("UsersRepository", "Resposta da API (login): $json")

            val jsonObject = JSONObject(json)
            val accessToken = jsonObject.getString("accessToken")
            val refreshToken = jsonObject.getString("refreshToken")
            val jsonUser = jsonObject.getJSONObject("user")

            AuthenticateService.saveToken(context, accessToken)
            AuthenticateService.saveRefreshToken(context, refreshToken)

            return try {
                val user = Users(
                    dataNascimento = LocalDateTime.parse(
                        jsonUser.getString("dataNascimento")
                    ),
                    nome = jsonUser.getString("name"),
                    genero = jsonUser.getString("genero"),
                    email = jsonUser.getString("email"),
                    cpf = jsonUser.getString("cpf"),
                    telefone = jsonUser.getString("telefone"),
                    type = TypeAccount.valueOf(jsonUser.getString("role")),
                    uuid = UUID.fromString(jsonUser.getString("uuid"))
                )
                Log.d("UsersRepository", "Usuário autenticado: $user")
                user
            } catch (e: Exception) {
                Log.e(
                    "UsersRepository",
                    "Erro ao criar usuário: ${e.message}",
                    e
                )
                throw Exception("Erro ao processar dados do usuário")
            }
        } else {
            Log.e(
                "UsersRepository",
                "Erro no login: código ${response.code()}"
            )
            throw Exception("Erro no login: ${response.code()}")
        }
    }

    override fun update(users: Users): Users {
        Log.d(
            "UsersRepository",
            "Iniciando atualização para o usuário: ${users.uuid}"
        )
        val response = api.update(users).execute()

        if (response.isSuccessful) {
            val json = response.body()?.string().orEmpty()
            Log.d("UsersRepository", "Resposta da API (update): $json")

            val jsonObject = JSONObject(json).getJSONObject("user")
            val updatedUser = Users(
                nome = jsonObject.getString("name"),
                dataNascimento = LocalDateTime.parse(
                    jsonObject.getString("dataNascimento")
                ),
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
            Log.e(
                "UsersRepository",
                "Erro ao atualizar usuário: código ${response.code()}"
            )
            throw Exception("Erro na atualização: ${response.code()}")
        }
    }
}
