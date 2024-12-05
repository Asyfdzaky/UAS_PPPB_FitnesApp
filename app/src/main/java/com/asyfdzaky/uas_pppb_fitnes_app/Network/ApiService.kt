package com.asyfdzaky.uas_pppb_fitnes_app.Network

import com.asyfdzaky.uas_pppb_fitnes_app.Model.Latihan.Latihan
import com.asyfdzaky.uas_pppb_fitnes_app.Model.User.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("user")
    fun getAllUsers(): Call<List<User>>

    @POST("user")
    fun createUser(@Body user: User): Call<User>

    @GET("latihan")
    fun getAllLatihan(): Call<List<Latihan>>

    @POST("latihan")
    fun createLatihan(@Body latihan: Latihan): Call<Latihan>

    @POST("latihan/{id}")
    fun updateLatihan(@Path("id") latihanId: String, @Body latihan: Latihan): Call<Latihan>

    @DELETE("latihan/{id}")
    fun deleteLatihan(@Path("id") latihanId: String): Call<Latihan>
}