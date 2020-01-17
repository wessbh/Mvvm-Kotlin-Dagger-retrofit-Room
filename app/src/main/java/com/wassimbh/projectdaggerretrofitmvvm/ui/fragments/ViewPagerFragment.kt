package com.wassimbh.projectdaggerretrofitmvvm.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.utils.adapters.ViewPagerAdapter


/**
 * A simple [Fragment] subclass.
 */
class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_view_pager, container, false)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        val tabLayout: TabLayout = view.findViewById(R.id.tabs)
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.view!!.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text!!){
                   // "Fire"-> tabLayout.setBackgroundColor(Color.valueOf(R.color.waterAccent))
                    "Fire"->  tab.view.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.fireAccent))
                    "Water"->  tab.view.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.waterAccent))
                    "Grass"->  tab.view.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.grassAccent))
                    "Fairy"->  tab.view.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.fairyAccent))
                    "Psychic"->  tab.view.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.psychicAccent))
                }
            }

        })

        tabLayout.setupWithViewPager(viewPager)
        val adapter = ViewPagerAdapter(childFragmentManager)
        // add your fragments
        adapter.addFragment(PokemonListFragment.newInstance("Fire"), "Fire")
        adapter.addFragment(PokemonListFragment.newInstance("Water"), "Water")
        adapter.addFragment(PokemonListFragment.newInstance("Grass"), "Grass")
        adapter.addFragment(PokemonListFragment.newInstance("Fairy"), "Fairy")
        adapter.addFragment(PokemonListFragment.newInstance("Psychic"), "Psychic")
        // set adapter on viewpager
        viewPager.adapter = adapter
    }
}
