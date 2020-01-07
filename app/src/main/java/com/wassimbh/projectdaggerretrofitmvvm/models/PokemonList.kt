package com.wassimbh.projectdaggerretrofitmvvm.models

import com.google.gson.annotations.SerializedName

 data class PokemonList(
    @SerializedName("cards")
    var pokemonList: List<Pokemon>
){
     override fun toString(): String {
         return "PokemonList(pokemonList=$pokemonList)"
     }
     fun getList(): List<Pokemon>{
         return this.pokemonList
     }
     fun setList(list: List<Pokemon>){
          this.pokemonList = list
     }
 }