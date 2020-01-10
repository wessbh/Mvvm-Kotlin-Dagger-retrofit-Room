package com.wassimbh.projectdaggerretrofitmvvm.ui.fragments

import androidx.lifecycle.MutableLiveData
import com.wassimbh.projectdaggerretrofitmvvm.base.BaseViewModel
import com.wassimbh.projectdaggerretrofitmvvm.models.Attacks

class AttackViewModel: BaseViewModel() {

    private val attackName = MutableLiveData<String>()
    private val attackDamage = MutableLiveData<String>()


    fun bind(attacks: Attacks){
        attackName.value = attacks.name
        attackDamage.value = attacks.damage
    }

    fun getAttackName(): MutableLiveData<String>{
        return attackName
    }
    fun getAttackDamage(): MutableLiveData<String>{
        return attackDamage
    }
}