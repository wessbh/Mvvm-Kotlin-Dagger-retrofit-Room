package com.wassimbh.projectdaggerretrofitmvvm.utils.eventbus

import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon

class PokemonBus (pokemon: Pokemon, eventFrom: String) {
    var pokemon: Pokemon = pokemon
    var eventFrom: String = eventFrom

    override fun toString(): String {
        return "PokemonBus(pokemon=$pokemon, eventFrom='$eventFrom')"
    }

}