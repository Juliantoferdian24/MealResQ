// TODO: ini class buat atur bottom navigation bar dan animasinya

package com.example.mealresq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    // ini inisialisasi analytics biar bisa liat berapa banyak yg login dll
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private lateinit var tb: Toolbar
    private var activeFragment: Int? = null
    private var nextFragment: Int? = null

    // ini yang ngatur buka fragment mana kalo klik apa
    @Suppress("DEPRECATION")
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{ item ->

        if(item.itemId == R.id.navigation_map && activeFragment != 3){
            tb.title = "MAP"
            val mapFrag = MapFragment.newInstance()
            nextFragment = 3
            openFragment(mapFrag)
            return@OnNavigationItemSelectedListener true
        }

        else if(item.itemId == R.id.navigation_home && activeFragment != 1){
            tb.title = "HOME"
            val homeFragment = HomeFragment.newInstance()
            nextFragment = 1
            openFragment(homeFragment)
            return@OnNavigationItemSelectedListener true
        }

        else if(item.itemId == R.id.navigation_favorite && activeFragment != 2){
            tb.title = "FAVORITE"
            val favFragment = FavFragment.newInstance()
            nextFragment = 2
            openFragment(favFragment)
            return@OnNavigationItemSelectedListener true
        }

        else if(item.itemId == R.id.navigation_profile && activeFragment != 4){
            tb.title = "Profile"
            val profileFragment = ProfileFragment.newInstance()
            nextFragment = 4
            openFragment(profileFragment)
            return@OnNavigationItemSelectedListener true
        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        setContentView(R.layout.activity_main)

        tb = findViewById(R.id.toolbarr)
        setSupportActionBar(tb)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        if(savedInstanceState == null){
            toHome()
        }

        tb.title = ""
        tb.setTitleTextColor(resources.getColor(R.color.transparent))

        // ini inisialisasi bottomNavBar nya
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    // fungsi buat pindah fragment
    private fun openFragment(fragment: Fragment){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if(activeFragment!! > nextFragment!!){
            transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
        }
        else if(activeFragment!! < nextFragment!!){
            transaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left)
        }
        activeFragment = nextFragment
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    // fungsi buat kalo back button dipencet
    override fun onBackPressed() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        val selectedItemId = bottomNavigation.selectedItemId

        if (selectedItemId != R.id.navigation_home){
            toHome()
            super.onBackPressed()
        }
    }

    private fun toHome(){
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.selectedItemId = R.id.navigation_home
        activeFragment = 1
        nextFragment = 1
        tb.title = "HOME"
        tb.setTitleTextColor(resources.getColor(R.color.white))
        openFragment(HomeFragment.newInstance())
    }
}
