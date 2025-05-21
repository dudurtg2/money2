package com.tcc.money.network.api

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.Intefaces.IUsersRepository
import com.tcc.money.data.dto.Login
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Movements
import com.tcc.money.data.models.Users
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.Call

interface UsersApi{
    @GET("api/users/refresh-token")
     fun refreshToken(): Call<ResponseBody>

    @POST("api/users/save")
     fun save(@Body users: Users): Call<ResponseBody>

    @POST("api/user/login")
     fun login(@Body login: Login): Call<ResponseBody>

    @GET("api/user/check")
    fun check(): Call<ResponseBody>

    @PUT("api/users/update")
     fun update(@Body users: Users): Call<ResponseBody>
}