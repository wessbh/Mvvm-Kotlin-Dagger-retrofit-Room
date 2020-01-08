package com.wassimbh.projectdaggerretrofitmvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.wassimbh.projectdaggerretrofitmvvm.DB.PokemonDao
import com.wassimbh.projectdaggerretrofitmvvm.api.ApiServices
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class Repository @Inject constructor(private val apiServices:ApiServices, private val pokemonDao: PokemonDao ){

     fun getCardsRoom() = pokemonDao.getAll()

      fun insertAll(pokemonList: List<Pokemon>) = pokemonDao.insertAll(pokemonList)

     fun deleteAll() = pokemonDao.deleteAllPokemones()

     fun geCardsList(): LiveData<List<Pokemon>> = liveData (Dispatchers.IO){
            val retrievedCardsRoom = pokemonDao.getAll()
            if(retrievedCardsRoom.isEmpty()){
                val retrievedCardsApi = apiServices.getCardsCoroutine()
                pokemonDao.insertAll(retrievedCardsApi.getList())
            }
            else{
                pokemonDao.deleteAllPokemones()
                pokemonDao.insertAll(apiServices.getCardsCoroutine().getList())
            }
            emit(pokemonDao.getAll())
    }
}