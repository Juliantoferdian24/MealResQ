package com.example.mealresq

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout

class RestoranActivity: AppCompatActivity() {

    private lateinit var rvMenu: RecyclerView
    private var list: ArrayList<Menu> = arrayListOf()
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var fotoRestoran: ImageView

    companion object {
        const val STRINGNYA = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.restoran_layout)

        rvMenu = findViewById(R.id.rv_menu)
        rvMenu.setHasFixedSize(true)

        collapsingToolbar = findViewById(R.id.collapsing_toolbar)
        fotoRestoran = findViewById(R.id.gambar_restoran)

        initList()
        showRecyclerList()

        super.onCreate(savedInstanceState)
    }

    private fun showRecyclerList() {
        rvMenu.layoutManager = LinearLayoutManager(this)
        val listRestoranAdapter = MenuRestaurantAdapter(list)
        rvMenu.adapter = listRestoranAdapter
        rvMenu.adapter = listRestoranAdapter

        listRestoranAdapter.setOnItemClickCallBack(object: MenuRestaurantAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Menu) {
                Toast.makeText(applicationContext, data.name, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initList(){

        val extranya = intent.getStringExtra(STRINGNYA)

        when(extranya){
            "Roti Bakar Bang Ali" -> {
                list.addAll(MenuDataRoti.listData)
                collapsingToolbar.title = "Roti Bakar Bang Ali"
                fotoRestoran.setImageResource(R.drawable.martabak)
            }
            "Burger Bang Deni" -> {
                list.addAll(MenuDataBerger.listData)
                collapsingToolbar.title = "Burger Bang Deni"
                fotoRestoran.setImageResource(R.drawable.bergerhome)
            }
            "Rumah Makan Padang Setia" -> {
                list.addAll(MenuDataPadang.listData)
                collapsingToolbar.title = "Rumah Makan Padang Setia"
                fotoRestoran.setImageResource(R.drawable.padanghome)
            }
            "Sate Padang Pak Tomy" -> {
                list.addAll(MenuDataSate.listData)
                collapsingToolbar.title = "Sate Padang Pak Tomy"
                fotoRestoran.setImageResource(R.drawable.satehome)
            }
            "Nasi Goreng Pemuda" -> {
                list.addAll(MenuDataNasgor.listData)
                collapsingToolbar.title = "Nasi Goreng Pemuda"
                fotoRestoran.setImageResource(R.drawable.nasgorhome)
            }
        }

    }
}