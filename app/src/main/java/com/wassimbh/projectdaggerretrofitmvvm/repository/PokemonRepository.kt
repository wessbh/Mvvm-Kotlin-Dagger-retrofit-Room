package com.wassimbh.projectdaggerretrofitmvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.wassimbh.projectdaggerretrofitmvvm.DB.PokemonDao
import com.wassimbh.projectdaggerretrofitmvvm.api.ApiServices
import com.wassimbh.projectdaggerretrofitmvvm.models.Attacks
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val apiServices:ApiServices, private val pokemonDao: PokemonDao ){

    fun getCardsRoom() = pokemonDao.getAll()
    fun getCardsRoomByType(type: String) = pokemonDao.getAllByType(type)
    fun getAttacksRoom(pokemon_id: String) = pokemonDao.getAttacks(pokemon_id)
    fun insertPokemons(pokemonList: List<Pokemon>) = pokemonDao.insertPokemons(pokemonList)

    fun deletePokemones() = pokemonDao.deleteAllPokemones()
    fun deleteAttacks() = pokemonDao.deleteAttacks()
    fun deleteAttacksByPokemon(pokemon_id: String) = pokemonDao.deleteAttacksByPokemon(pokemon_id)

    fun getCardsList(): LiveData<List<Pokemon>> = liveData (Dispatchers.IO){
            val retrievedCardsRoom = getCardsRoom()
            val retrievedCardsApi = apiServices.getCardsCoroutine().getList()
            if(retrievedCardsRoom.isEmpty()){
                retrievedCardsApi.forEach{pokemon->
                    addPokemonIdToAttacks(pokemon, pokemon.attacks)
                }
                insertPokemons(retrievedCardsApi)
            }
            else{
                deletePokemones()
                deleteAttacks()
                retrievedCardsApi.forEach{pokemon->
                    addPokemonIdToAttacks(pokemon, pokemon.attacks)
                }
                insertPokemons(retrievedCardsApi)
            }
            emit(pokemonDao.getAll())
    }

    fun getCardsListType(type: String): LiveData<List<Pokemon>> = liveData (Dispatchers.IO){
        val retrievedCardsRoom = getCardsRoomByType(type)
        val retrievedCardsApi = apiServices.getCardsCoroutineType(type).getList()
        if(retrievedCardsRoom.isEmpty()){
            retrievedCardsApi.forEach{pokemon->
                addPokemonIdToAttacks(pokemon, pokemon.attacks)
            }
            insertPokemons(retrievedCardsApi)
        }
        else{
            deletePokemones()
            deleteAttacks()
            retrievedCardsApi.forEach{pokemon->
                addPokemonIdToAttacks(pokemon, pokemon.attacks)
            }
            insertPokemons(retrievedCardsApi)
        }

        emit(pokemonDao.getAllByType(type))
    }

    fun getCardsListTypeOffline(type: String): LiveData<List<Pokemon>> = liveData (Dispatchers.IO){
        emit(pokemonDao.getAllByType(type))
    }

    private fun addPokemonIdToAttacks(pokemon: Pokemon, attacksList: List<Attacks>){
            if(attacksList.isNotEmpty()){
                pokemonDao.deleteAttacksByPokemon(pokemon.id)
                attacksList.forEach{attack->
                    if(attack.damage == ""){
                        attack.damage = "No entry"
                    }
                    attack.pokemone_id = pokemon.id
                }
                pokemonDao.insertAttacks(attacksList)
            }
        }

    fun getAttacks(pokemon_id: String): LiveData<List<Attacks>> = liveData (Dispatchers.IO){
        val attacks = getAttacksRoom(pokemon_id)
        emit(attacks)
    }

}