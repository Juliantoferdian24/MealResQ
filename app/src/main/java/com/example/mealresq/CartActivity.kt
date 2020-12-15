package com.example.mealresq

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.alert_dialog.view.*

class CartActivity: AppCompatActivity() {

    private lateinit var rvCheckout: RecyclerView
    private var list: ArrayList<MenuCheckout> = arrayListOf()
    private lateinit var listRestoranAdapter: CheckoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.cart_layout)

        rvCheckout = findViewById(R.id.rv_checkout)

        list.addAll(MenuDataCart.listData)
        showRecyclerList()

        super.onCreate(savedInstanceState)
    }

    private fun showRecyclerList() {
        rvCheckout.layoutManager = LinearLayoutManager(this)
        listRestoranAdapter = CheckoutAdapter(list)
        rvCheckout.adapter = listRestoranAdapter

        listRestoranAdapter.setOnItemClickCallBack(object: CheckoutAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MenuCheckout, pos: Int) {
                showDialogBox(pos)
                Log.d("HOHO", pos.toString())
            }

        })
    }

    private fun showDialogBox(position: Int){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Hapus dari cart?")
        val mAlertDialog = mBuilder.show()
        mDialogView.okay.setOnClickListener {
            mAlertDialog.dismiss()
            // TODO: 05/11/20 kalo click okay
            MenuDataCart.menuNames.removeAt(position)
            MenuDataCart.qtyMenu.removeAt(position)
            MenuDataCart.menuPrice.removeAt(position)

            list.clear()
            list.addAll(MenuDataCart.listData)
            listRestoranAdapter.notifyDataSetChanged()
        }
        mDialogView.cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}