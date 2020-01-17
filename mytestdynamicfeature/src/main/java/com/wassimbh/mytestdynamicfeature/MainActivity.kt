package com.wassimbh.mytestdynamicfeature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintestdfm)
        changeFragment(DfmFragment())
    }


    fun changeFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(com.wassimbh.projectdaggerretrofitmvvm.R.anim.design_bottom_sheet_slide_in, com.wassimbh.projectdaggerretrofitmvvm.R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.frame_container, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
    }
}
