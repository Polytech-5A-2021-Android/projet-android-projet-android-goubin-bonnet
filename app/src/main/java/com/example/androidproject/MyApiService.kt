package com.example.androidproject

import com.example.androidproject.model.Localisation
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "http://10.0.2.2:5000"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface MyApiService {
    @GET("/localisation/get_localisation_on_time")
    fun getLocalisation(): Deferred<List<Localisation>>
    @GET("localisation/get_last_localisation")
    fun getLastLocation(): Deferred<List<Localisation>>
}

object MyApi {
    val retrofitService: MyApiService by lazy { retrofit.create(MyApiService::class.java) }
}
