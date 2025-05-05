package com.tcc.money.network.api

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.Intefaces.IUsersRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Movements
import com.tcc.money.data.models.Users
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsersApi: IUsersRepository {
    @GET("api/users/find/{id}")
    override suspend fun findById(@Path("id") id: Long): Users

    @POST("api/users/save")
    override suspend fun save(@Body users: Users): Users

    @POST("api/users/login")
    override suspend fun login(users: Users): Users

    @PUT("api/users/update")
    override suspend fun update(@Body users: Users): Users
}