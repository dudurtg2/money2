package com.tcc.money.network.api

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.Intefaces.IUsersRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Movements
import com.tcc.money.data.models.Users
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsersApi{
    @GET("api/users/find/{id}")
    suspend fun findById(@Path("id") id: Long): Response<ResponseBody>

    @POST("api/users/save")
    suspend fun save(@Body users: Users): Response<ResponseBody>

    @POST("api/users/login")
    suspend fun login(users: Users): Response<ResponseBody>

    @PUT("api/users/update")
    suspend fun update(@Body users: Users): Response<ResponseBody>
}