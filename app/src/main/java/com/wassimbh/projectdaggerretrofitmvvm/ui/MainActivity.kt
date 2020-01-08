package com.wassimbh.projectdaggerretrofitmvvm.ui

import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.databinding.ActivityMainBinding
import com.wassimbh.projectdaggerretrofitmvvm.injection.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        binding.myRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.myRecyclerView.setHasFixedSize(true)
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(MainActivityViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel
        showPoke()
    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }
    private fun showPoke(){
        try {
            viewModel.cardsList.observe(this, Observer {list->
                if(list.isNotEmpty()){
                    viewModel.onRetrieveCardsListFinish()
                    Log.d("mriGel", "Done!!!"+list)
                    viewModel.pokemonMutableLiveData.value = list
                    viewModel.onRetrieveCardsListSuccess()
                }
                else
                    viewModel.onRetrieveCardsListError()
            })
        }
        catch (e: Throwable){
            viewModel.onRetrieveCardsListError()
        }
    }
}
