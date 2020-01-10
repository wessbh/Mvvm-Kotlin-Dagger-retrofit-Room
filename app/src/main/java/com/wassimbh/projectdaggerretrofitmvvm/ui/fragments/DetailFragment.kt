package com.wassimbh.projectdaggerretrofitmvvm.ui.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.databinding.FragmentDetailBinding
import com.wassimbh.projectdaggerretrofitmvvm.injection.ViewModelFactory
import com.wassimbh.projectdaggerretrofitmvvm.models.Attacks
import com.wassimbh.projectdaggerretrofitmvvm.models.Pokemon
import com.wassimbh.projectdaggerretrofitmvvm.utils.eventbus.PokemonBus
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment: Fragment() {
    lateinit var pokemon: Pokemon
    lateinit var attacks: Attacks
    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailBinding
    lateinit var myContext: Context
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        binding = DataBindingUtil.setContentView(activity!!, R.layout.fragment_detail)
        binding.myRecyclerView.layoutManager = GridLayoutManager(myContext, 2)
        binding.myRecyclerView.setHasFixedSize(true)
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(DetailsViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        Picasso
            .get()
            .load(pokemon.imageUrl)
            .into(binding.pokeImg)
        binding.pokeName.text = pokemon.name
        binding.viewModel = viewModel
        showAttacks()
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(pokemon: Pokemon) = DetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable("pokemon", pokemon )
            }
        }
    }

    fun showAttacks(){
        viewModel.attacksList(pokemon.id).observe(this, Observer{list->
            viewModel.attacksMutableLiveData.value = list!!
            viewModel.onRetrieveAttacksListStart()
            viewModel.onRetrieveAttakcsListSuccess()
            viewModel.onRetrieveAttakcsListFinish()
        })
    }
    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = activity!!
        pokemon = arguments?.getSerializable("pokemon") as Pokemon
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
        Toast.makeText(myContext,  event.pokemon.name+" - " + event.eventFrom, Toast.LENGTH_SHORT).show()
    }
}
