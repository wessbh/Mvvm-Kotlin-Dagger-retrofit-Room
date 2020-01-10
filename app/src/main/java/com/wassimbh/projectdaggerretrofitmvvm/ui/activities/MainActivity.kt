package com.wassimbh.projectdaggerretrofitmvvm.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.databinding.ActivityMainBinding
import com.wassimbh.projectdaggerretrofitmvvm.injection.ViewModelFactory
import com.wassimbh.projectdaggerretrofitmvvm.utils.PokemonBus
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.myRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.myRecyclerView.setHasFixedSize(true)
       /* binding.myRecyclerView.addOnItemTouchListener(CustomRVItemTouchListener(applicationContext, binding.myRecyclerView,object : RecyclerViewItemClickListener{
            override fun onClick(view: View, position: Int) {
                viewModel.cardsList.observe(this@MainActivity, Observer {list->
                    val pokemon = list[position]
                    val attacks = viewModel.attacksList(pokemon.id)
                    attacks.observe(this@MainActivity, Observer {attacksList->
                        val attack = attacksList[0]
                        showDialog()
                    })
                })
            }

            override fun onLongClick(view: View, position: Int) {
            }

        }))*/
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(MainActivityViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel
        showPoke()
    }
    private fun showDialog(){
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.show()
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

    @Override
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    @Override
    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventFired(event: PokemonBus){
        Toast.makeText(applicationContext,  event.pokemon.name+" - " + event.eventFrom, Toast.LENGTH_SHORT).show()
    }

}
