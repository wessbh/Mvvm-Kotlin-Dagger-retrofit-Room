package com.wassimbh.projectdaggerretrofitmvvm.models

import androidx.room.Embedded
import androidx.room.Relation

data class PokemonAndAttacks (
    @Embedded
    var pokemon: Pokemon,
    @Relation(parentColumn = "id", entityColumn = "")
    var attackList: List<Attacks>
)