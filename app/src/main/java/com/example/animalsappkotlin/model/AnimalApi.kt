package com.example.animalsappkotlin.model

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi {

    @GET("getKey")
    fun getAPIKey() : Single<ApiKey>

    @FormUrlEncoded
    @POST("getAnimals")
    fun getAnimalsApi(@Field("key") key : String) : Single<List<Animals>>
}