package com.wassimbh.projectdaggerretrofitmvvm.api

import com.wassimbh.projectdaggerretrofitmvvm.models.PokemonList
import retrofit2.http.GET

interface ApiServices {

    @GET("cards?types=fire")
    suspend fun getCardsCoroutine(): PokemonList
}