package com.kdan.randomjokes.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://official-joke-api.appspot.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface JokeApiService {

    @GET("random_joke")
    suspend fun getOneJoke() : Joke

    @GET("random_ten")
    suspend fun getTenJokes() : List<Joke>
}

object JokeApi {
    val retrofitService: JokeApiService by lazy {
        retrofit.create(JokeApiService::class.java)
    }
}