package com.example.mealresq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity: AppCompatActivity() {

    private lateinit var rvCheckout: RecyclerView
    private var list: ArrayList<MenuCheckout> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.cart_layout)

        rvCheckout = findViewById(R.id.rv_checkout)

        list.addAll(MenuDataCart.listData)
        showRecyclerList()

        super.onCreate(savedInstanceState)
    }

    private fun showRecyclerList() {
        rvCheckout.layoutManager = LinearLayoutManager(this)
        val listRestoranAdapter = CheckoutAdapter(list)
        rvCheckout.adapter = listRestoranAdapter

        listRestoranAdapter.setOnItemClickCallBack(object: CheckoutAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MenuCheckout) {
                TODO("Not yet implemented")
            }

        })
    }
}