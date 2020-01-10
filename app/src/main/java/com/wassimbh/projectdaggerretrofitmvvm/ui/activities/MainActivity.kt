package com.wassimbh.projectdaggerretrofitmvvm.ui.activities

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.ui.fragments.PokemonListFragment
import com.wassimbh.projectdaggerretrofitmvvm.utils.eventbus.PokemonBus
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManager = supportFragmentManager
        val e= findViewById<FrameLayout>(R.id.framelayout)
        changeFragment(PokemonListFragment())
    }
    fun changeFragment(fragment: Fragment){
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.add(R.id.framelayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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
       /* val e= findViewById<FrameLayout>(R.id.framelayout)
        val detailFragment: DetailFragment = DetailFragment.newInstance(event.pokemon)
        changeFragment(detailFragment)*/
    }
}
