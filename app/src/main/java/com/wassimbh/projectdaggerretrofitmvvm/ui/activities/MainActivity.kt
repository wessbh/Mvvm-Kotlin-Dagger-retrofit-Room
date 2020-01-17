package com.wassimbh.projectdaggerretrofitmvvm.ui.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.wassimbh.projectdaggerretrofitmvvm.R
import com.wassimbh.projectdaggerretrofitmvvm.injection.ViewModelFactory
import com.wassimbh.projectdaggerretrofitmvvm.ui.fragments.ViewPagerFragment

class MainActivity : AppCompatActivity() {

    private lateinit var splitInstallManager : SplitInstallManager

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(MainActivityViewModel::class.java)
        changeFragment(ViewPagerFragment())
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom_navigation.setOnNavigationItemSelectedListener(myNavigationItemSelectedListener)
        installDFM()
    }
    private val myNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.pokedex -> {
                changeFragment(ViewPagerFragment())
                Toast.makeText(applicationContext, "This is pokedex", Toast.LENGTH_SHORT).show()
            }
            R.id.home -> {
                val feature_name: String = "mytestdynamicfeature"
                if (splitInstallManager.installedModules.contains(feature_name)) {
                    changeFragment(loadFragment(feature_name,"DfmFragment"))
                } else {
                    Toast.makeText(applicationContext, "DFM failed, shit !", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(applicationContext, "This is Home sweet home", Toast.LENGTH_SHORT).show()
            }
        }
        false
    }
    fun changeFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.frame_container, fragment)
            .addToBackStack(null)
            .commit()
    }
    private fun installDFM() {
        /*val sdCard: File = application!!.getExternalFilesDir(null)!!
        val file = File(sdCard, "splits")*/
        splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
        val request = SplitInstallRequest.newBuilder()
            .addModule("myseconddynamicfeature")
            .build()

        splitInstallManager.startInstall(request)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "DFM installed", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "DFM failed, shit !", Toast.LENGTH_SHORT).show()
            }
    }
    private fun loadFragment(feature_name: String, fragment_name: String): Fragment{
        return Class.forName("com.wassimbh."+feature_name+"."+fragment_name).newInstance() as Fragment
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun checkNetworkState(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}
/*
val pokemon = Pokemon("xy11-25","Volcanion", "721", "imageUrl\":\"https://images.pokemontcg.io/xy11/25.png",
    "imageUrlHiRes\":\"https://images.pokemontcg.io/xy11/volcano.png",
    arrayOf("Fire"),listOf<Attacks>())*/
