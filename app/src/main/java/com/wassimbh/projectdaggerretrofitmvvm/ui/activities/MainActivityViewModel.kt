package com.wassimbh.projectdaggerretrofitmvvm.ui.activities

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wassimbh.projectdaggerretrofitmvvm.DB.PokemonDao
import com.wassimbh.projectdaggerretrofitmvvm.api.ApiServices
import com.wassimbh.projectdaggerretrofitmvvm.base.BaseViewModel
import com.wassimbh.projectdaggerretrofitmvvm.models.Attacks
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon
import com.wassimbh.projectdaggerretrofitmvvm.repository.PokemonRepository
import com.wassimbh.projectdaggerretrofitmvvm.utils.adapters.AttacksAdapter
import com.wassimbh.projectdaggerretrofitmvvm.utils.adapters.CardsRecyclerViewAdapter
import javax.inject.Inject

class MainActivityViewModel: BaseViewModel(){

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    @Inject
    lateinit var apiServices: ApiServices
    @Inject
    lateinit var pokemonDao: PokemonDao
    @Inject
    lateinit var pokemonRepository: PokemonRepository


    val pokemonListAdapter: CardsRecyclerViewAdapter = CardsRecyclerViewAdapter()
    val attacksAdapter: AttacksAdapter = AttacksAdapter()
    val pokemonMutableLiveData: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val attacksMutableLiveData: MutableLiveData<List<Attacks>> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener {  }
    init{
        onRetrieveCardsListStart()
        Log.d("mriGel", "View Model Created")
    }

    fun cardsList(): LiveData<List<Pokemon>> {
        return  pokemonRepository.getCardsList()
    }

    fun cardsListType(type: String): LiveData<List<Pokemon>> {
        return  pokemonRepository.getCardsListType(type)
    }
    fun cardsListTypeOffline(type: String): LiveData<List<Pokemon>> {
        return  pokemonRepository.getCardsListTypeOffline(type)
    }


    private fun onRetrieveCardsListStart(){
        loadingVisibility.value = View.VISIBLE
    }
    fun onRetrieveCardsListFinish(){
        loadingVisibility.value = View.GONE
        errorMessage.value = null
    }

    fun onRetrieveCardsListError(msgError: Int){
        errorMessage.value = msgError
    }
    override fun onCleared() {
        super.onCleared()
    }
}