package com.wassimbh.projectdaggerretrofitmvvm.ui.activities

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wassimbh.projectdaggerretrofitmvvm.DB.PokemonDao
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.api.ApiServices
import com.wassimbh.projectdaggerretrofitmvvm.base.BaseViewModel
import com.wassimbh.projectdaggerretrofitmvvm.models.Attacks
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon
import com.wassimbh.projectdaggerretrofitmvvm.repository.PokemonRepository
import com.wassimbh.projectdaggerretrofitmvvm.utils.adapters.CardsRecyclerViewAdapter
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivityViewModel: BaseViewModel(){
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    @Inject
    lateinit var apiServices: ApiServices
    @Inject
    lateinit var pokemonDao: PokemonDao
    @Inject
    lateinit var pokemonRepository: PokemonRepository


    private lateinit var subscription: Disposable
    val pokemonListAdapter: CardsRecyclerViewAdapter =
        CardsRecyclerViewAdapter()
    val pokemonMutableLiveData: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val attacksMutableLiveData: MutableLiveData<List<Attacks>> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener {  }
    init{
        onRetrieveCardsListStart()
    }

    val cardsList: LiveData<List<Pokemon>> = pokemonRepository.geCardsList()
    fun attacksList(pokemon_id: String) :LiveData<List<Attacks>>{
        val attacks: LiveData<List<Attacks>> = pokemonRepository.gettAttacks(pokemon_id)

       return attacks
    }
     private fun onRetrieveCardsListStart(){
        loadingVisibility.value = View.VISIBLE
    }

     fun onRetrieveCardsListFinish(){
        loadingVisibility.value = View.GONE
        errorMessage.value = null
    }

     fun onRetrieveCardsListSuccess(){
        Log.d("mriGel", "Done!")
       pokemonListAdapter.updateCardList(pokemonMutableLiveData.value!!)
    }

     fun onRetrieveCardsListError(){
        errorMessage.value =
            R.string.my_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}