package com.wassimbh.projectdaggerretrofitmvvm.ui.fragments

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wassimbh.projectdaggerretrofitmvvm.DB.PokemonDao
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.api.ApiServices
import com.wassimbh.projectdaggerretrofitmvvm.base.BaseViewModel
import com.wassimbh.projectdaggerretrofitmvvm.models.Attacks
import com.wassimbh.projectdaggerretrofitmvvm.repository.PokemonRepository
import com.wassimbh.projectdaggerretrofitmvvm.utils.adapters.AttacksAdapter
import javax.inject.Inject

class DetailsViewModel (): BaseViewModel() {

    @Inject
    lateinit var apiServices: ApiServices
    @Inject
    lateinit var pokemonDao: PokemonDao
    @Inject
    lateinit var pokemonRepository: PokemonRepository
    val loadingAttacksVisibility: MutableLiveData<Int> = MutableLiveData()
    val attacksAdapter: AttacksAdapter = AttacksAdapter()
    val attacksMutableLiveData: MutableLiveData<List<Attacks>> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener {  }
    init{
        onRetrieveAttacksListStart()
    }

    fun attacksList(pokemon_id: String) : LiveData<List<Attacks>> {
        val attacks: LiveData<List<Attacks>> = pokemonRepository.gettAttacks(pokemon_id)

        return attacks
    }


     fun onRetrieveAttacksListStart(){
        loadingAttacksVisibility.value = View.VISIBLE
    }


    fun onRetrieveAttacksListError(){
        errorMessage.value =
            R.string.my_error
    }
    fun onRetrieveAttakcsListSuccess(){
        Log.d("mriGel", "Done!")
        attacksAdapter.updateCardList(attacksMutableLiveData.value!!)
    }
    fun onRetrieveAttakcsListFinish(){
        loadingAttacksVisibility.value = View.GONE
        errorMessage.value = null
    }
}