package com.wassimbh.projectdaggerretrofitmvvm.models

import androidx.room.Entity

@Entity
data class Attacks (
    var cost: String,
    var name: String,
    var text: String,
    var damage: Int,
    var convertedEnergyCost: Int
)