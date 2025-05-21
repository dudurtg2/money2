package com.tcc.money.network.api
import com.tcc.money.data.models.Coins
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface CoinsApi {
    @GET("api/coins/findAll")
    fun findAll(): Call<ResponseBody>

    @GET("api/coins/find/{uuid}")
    fun findByUUID(
        @Path("uuid") uuid: UUID
    ): Call<ResponseBody>

    @POST("api/coins/create")
    fun save(
        @Body coins: Coins
    ): Call<ResponseBody>

    @DELETE("api/coins/delete/{uuid}")
    fun delete(
        @Path("uuid") uuid: UUID
    )

    @PUT("api/coins/update/{uuid}")
    fun update(
        @Path("uuid") uuid: UUID,
        @Body coins: Coins
    ): Call<ResponseBody>
}
