package com.wassimbh.projectdaggerretrofitmvvm.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.wassimbh.projectdaggerretrofitmvvm.DB.PokemonDao
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.api.ApiServices
import com.wassimbh.projectdaggerretrofitmvvm.base.BaseViewModel
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon
import com.wassimbh.projectdaggerretrofitmvvm.utils.CardsRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivityViewModel(private val pokemonDao: PokemonDao): BaseViewModel(){
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    @Inject
    lateinit var apiServices: ApiServices
    private lateinit var subscription: Disposable
    val pokemonListAdapter: CardsRecyclerViewAdapter = CardsRecyclerViewAdapter()

    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadCards() }
    init{
        loadCards()
    }
    private fun loadCards(){
        subscription = Observable.fromCallable { pokemonDao.getAll() }
            .concatMap {
                    dbPokemonList ->
                if(dbPokemonList.isEmpty()){
                    apiServices.getCards().concatMap {
                            apiPostList -> pokemonDao.insertAll(apiPostList.getList())
                        Observable.just(apiPostList)
                    }
                }
                else {
                    Observable.just(dbPokemonList)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveCardsListStart() }
            .doOnTerminate { onRetrieveCardsListFinish() }
            .subscribe(
                {result ->
                    var list = result as List<Pokemon>
                    Log.d("mriGell", "in result !$result")
                    onRetrieveCardsListSuccess(list) },
                { err-> onRetrieveCardsListError(err) }
            )
    }

    private fun onRetrieveCardsListStart(){
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveCardsListFinish(){
        loadingVisibility.value = View.GONE
        errorMessage.value = null
    }

    private fun onRetrieveCardsListSuccess(pokemonList: List<Pokemon>){
        Log.d("mriGel", "Done!")
       pokemonListAdapter.updateCardList(pokemonList)
    }

    private fun onRetrieveCardsListError(err: Throwable){
        Log.d("mriGel", ""+err)
        errorMessage.value =
            R.string.my_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}