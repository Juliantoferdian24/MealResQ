// TODO: ini class buat atur bottom navigation bar dan animasinya

package com.example.mealresq

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import java.util.jar.Manifest

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
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermission()

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

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

    private fun checkPermission(){
        if(ContextCompat.checkSelfPermission(this@MainActivity,
            android.Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }else{
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if((ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED)){
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
