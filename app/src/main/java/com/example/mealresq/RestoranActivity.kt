package com.example.mealresq

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.alert_dialog.view.*
import kotlinx.coroutines.Delay
import java.util.*
import kotlin.collections.ArrayList


class RestoranActivity: AppCompatActivity() {

    private lateinit var rvMenu: RecyclerView
    private var list: ArrayList<Menu> = arrayListOf()
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var fotoRestoran: ImageView
    private lateinit var phone: ImageView
    private lateinit var instagram: ImageView
    private lateinit var favIcon: ImageView

    private var isFavorite: Boolean = false
    private lateinit var myResto: Restaurant
    private lateinit var auth: FirebaseAuth
    private var extranya = ""

    private lateinit var telepon: String
    private lateinit var akunInstagram: String


    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference
    private lateinit var listRestoranAdapter: MenuRestaurantAdapter

    companion object {
        const val STRINGNYA = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.restoran_layout)

        rvMenu = findViewById(R.id.rv_menu)
        rvMenu.setHasFixedSize(true)

        collapsingToolbar = findViewById(R.id.collapsing_toolbar)
        fotoRestoran = findViewById(R.id.gambar_restoran)
        phone = findViewById(R.id.phone)
        instagram = findViewById(R.id.instagram)
        favIcon = findViewById(R.id.favorite)
        auth = FirebaseAuth.getInstance()

        ngambilDataFirebase()
        initList()
        showRecyclerList()
        checkFirebase(extranya)

        phone.setOnClickListener(View.OnClickListener {

            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions(
                        this@RestoranActivity,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        101
                    )
                    return@OnClickListener
                }
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + telepon)
                startActivity(callIntent)
            } else {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + telepon)
                startActivity(callIntent)
            }
        })
        instagram.setOnClickListener(View.OnClickListener {
            val uri = Uri.parse("http://instagram.com/_u/${akunInstagram}")
            val likeIng = Intent(Intent.ACTION_VIEW, uri)
            likeIng.setPackage("com.instagram.android")
            try {
                startActivity(likeIng)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/${akunInstagram}")
                    )
                )
            }
        })
        super.onCreate(savedInstanceState)
    }

    private fun ngambilDataFirebase(){
        val extranya = intent.getStringExtra(STRINGNYA)
        myRef.child("restoran").child(extranya)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    telepon = snapshot.child("phone").value.toString()
                    akunInstagram = snapshot.child("akunInstagram").value.toString()
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showRecyclerList() {
        rvMenu.layoutManager = LinearLayoutManager(this)
        listRestoranAdapter = MenuRestaurantAdapter(list)
        rvMenu.adapter = listRestoranAdapter
        rvMenu.adapter = listRestoranAdapter

        listRestoranAdapter.setOnItemClickCallBack(object :
            MenuRestaurantAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Menu) {
                showDialogBox(data)
            }
        })
    }

    private fun initList(){

        extranya = intent.getStringExtra(STRINGNYA)

        when(extranya){
            "Roti Bakar Bang Ali" -> {
                addFromFirebase(list, "Roti Bakar Bang Ali")
                collapsingToolbar.title = "Roti Bakar Bang Ali"
                fotoRestoran.setImageResource(R.drawable.martabak)
            }
            "Burger Bang Deni" -> {
                addFromFirebase(list, "Burger Bang Deni")
                collapsingToolbar.title = "Burger Bang Deni"
                fotoRestoran.setImageResource(R.drawable.bergerhome)
            }
            "Rumah Makan Padang Setia" -> {
                addFromFirebase(list, "Rumah Makan Padang Setia Bener")
                collapsingToolbar.title = "Rumah Makan Padang Setia"
                fotoRestoran.setImageResource(R.drawable.padanghome)
            }
            "Sate Padang Pak Tomy" -> {
                addFromFirebase(list, "Rumah Makan Padang Setia")
                collapsingToolbar.title = "Sate Padang Pak Tomy"
                fotoRestoran.setImageResource(R.drawable.satehome)
            }
            "Nasi Goreng Pemuda" -> {
                addFromFirebase(list, "Nasi Goreng Pemuda")
                collapsingToolbar.title = "Nasi Goreng Pemuda"
                fotoRestoran.setImageResource(R.drawable.nasgorhome)
            }
        }

    }

    fun cartClick(view: View) {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun showDialogBox(data: Menu){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Tambahkan kedalam cart?")
        val mAlertDialog = mBuilder.show()
        mDialogView.okay.setOnClickListener {
            mAlertDialog.dismiss()
            // TODO: 05/11/20 kalo click okay
            MenuDataCart.menuNames.add(data.name)
            MenuDataCart.qtyMenu.add("1x")
            MenuDataCart.menuPrice.add(data.price)
        }
        mDialogView.cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    private fun addFromFirebase(list: ArrayList<Menu>, namaRestoran: String){
        myRef.child("restoran").child(namaRestoran).child("menu")
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    telepon = snapshot.child("phone").value.toString()
                    for (key in snapshot.children){
                        val menu = Menu()

                        menu.name = key.child("namamenu").value.toString()
                        menu.description = key.child("deskripsi").value.toString()
                        menu.price = key.child("harga").value.toString()
                        menu.photo = key.child("fotomenu").value.toString()

                        list.add(menu)
                    }
                    listRestoranAdapter.notifyDataSetChanged()
                }
            })
    }

    private fun pushToFavorite(namaRestoran: String){
        myRef.child("Users").child(auth.currentUser!!.uid).child("favorites").child(namaRestoran)
            .setValue(namaRestoran)
    }

    private fun removeFavorite(namaRestoran: String){
        myRef.child("Users").child(auth.currentUser!!.uid).child("favorites").child(namaRestoran)
            .removeValue()
    }

    private fun checkFirebase(namaRestoran: String){
        myRef.child("Users").child(auth.currentUser!!.uid).child("favorites")
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        val trigger = snapshot.child(namaRestoran).value.toString()
                        isFavorite = true
                        favIcon.setBackgroundResource(R.drawable.ic_fav_fill)
                    }catch (e: Exception){
                        isFavorite = false
                        favIcon.setBackgroundResource(R.drawable.ic_fav)
                    }
                }
            })
    }

    fun favoriteClick(view: View) {
        if (isFavorite){
            favIcon.setBackgroundResource(R.drawable.ic_fav)
            removeFavorite(extranya)
            isFavorite = false
        }else{
            favIcon.setBackgroundResource(R.drawable.ic_fav_fill)
            pushToFavorite(extranya)
            isFavorite = true
        }

    }
}