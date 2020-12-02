package com.example.mealresq

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.alert_dialog.view.*


class RestoranActivity: AppCompatActivity() {

    private lateinit var rvMenu: RecyclerView
    private var list: ArrayList<Menu> = arrayListOf()
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var fotoRestoran: ImageView
    private lateinit var phone: ImageView
    private lateinit var instagram: ImageView

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

        initList()
        showRecyclerList()
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
                callIntent.data = Uri.parse("tel:" + "083890769297")
                startActivity(callIntent)
            } else {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + "083890769297")
                startActivity(callIntent)
            }
        })
        instagram.setOnClickListener(View.OnClickListener {
            val uri = Uri.parse("http://instagram.com/_u/burgerking.id")
            val likeIng = Intent(Intent.ACTION_VIEW, uri)

            likeIng.setPackage("com.instagram.android")

            try {
                startActivity(likeIng)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/burgerking.id")
                    )
                )
            }
        })

        super.onCreate(savedInstanceState)
    }

    private fun showRecyclerList() {
        rvMenu.layoutManager = LinearLayoutManager(this)
        listRestoranAdapter = MenuRestaurantAdapter(list)
        rvMenu.adapter = listRestoranAdapter
        rvMenu.adapter = listRestoranAdapter

        listRestoranAdapter.setOnItemClickCallBack(object :
            MenuRestaurantAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Menu) {
                showDialogBox()
            }
        })
    }

    private fun initList(){

        val extranya = intent.getStringExtra(STRINGNYA)

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

    private fun showDialogBox(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Tambahkan kedalam cart?")
        val mAlertDialog = mBuilder.show()
        mDialogView.okay.setOnClickListener {
            mAlertDialog.dismiss()
            // TODO: 05/11/20 kalo click okay
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
}