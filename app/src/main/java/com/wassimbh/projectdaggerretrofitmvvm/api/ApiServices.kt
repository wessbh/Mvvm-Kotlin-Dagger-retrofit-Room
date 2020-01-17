package com.wassimbh.projectdaggerretrofitmvvm.api

import com.wassimbh.projectdaggerretrofitmvvm.models.PokemonList
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("cards?types=fire")
    suspend fun getCardsCoroutine(): PokemonList

    @GET("cards")
    suspend fun getCardsCoroutineType(@Query("types") type: String): PokemonList
}