package com.wassimbh.projectdaggerretrofitmvvm.api

import com.wassimbh.projectdaggerretrofitmvvm.models.PokemonList
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiServices {

    @GET("cards?types=fire")
    fun getCards():Observable<PokemonList>
}