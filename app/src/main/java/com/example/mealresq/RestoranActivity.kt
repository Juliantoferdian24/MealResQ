package com.example.mealresq

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RestoranActivity: AppCompatActivity() {

    private lateinit var rvMenu: RecyclerView
    private var list: ArrayList<Restaurant> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.restoran_layout)

        rvMenu = findViewById(R.id.rv_menu)
        rvMenu.setHasFixedSize(true)

        list.addAll(RestoranData.listData)
        showRecyclerList()

        super.onCreate(savedInstanceState)
    }

    private fun showRecyclerList() {
        rvMenu.layoutManager = LinearLayoutManager(this)
        val listRestoranAdapter = ListRestaurantAdapter(list)
        rvMenu.adapter = listRestoranAdapter
        rvMenu.adapter = listRestoranAdapter

        listRestoranAdapter.setOnItemClickCallBack(object: ListRestaurantAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Restaurant) {
                Toast.makeText(applicationContext, data.name, Toast.LENGTH_SHORT).show()
            }
        })
    }
}