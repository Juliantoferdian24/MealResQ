package com.example.mealresq

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.alert_dialog.view.*

class CartActivity: AppCompatActivity() {

    private lateinit var rvCheckout: RecyclerView
    private var list: ArrayList<MenuCheckout> = arrayListOf()
    private lateinit var listRestoranAdapter: CheckoutAdapter
    private lateinit var buttonCart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.cart_layout)

        rvCheckout = findViewById(R.id.rv_checkout)

        list.addAll(MenuDataCart.listData)
        showRecyclerList()

        inisialisasiButton()

        super.onCreate(savedInstanceState)
    }

    private fun showRecyclerList() {
        rvCheckout.layoutManager = LinearLayoutManager(this)
        listRestoranAdapter = CheckoutAdapter(list)
        rvCheckout.adapter = listRestoranAdapter

        listRestoranAdapter.setOnItemClickCallBack(object: CheckoutAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MenuCheckout, pos: Int) {
                showDialogBox(pos)
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

    @SuppressLint("SetTextI18n")
    private fun inisialisasiButton(){
        buttonCart = findViewById(R.id.button_cart)
        var total = 0

        for(prais in MenuDataCart.menuPrice){
            val harganya: Int = prais.substringBefore(".").removeRange(0, 3).toInt()
            total += harganya
            Log.d("Testdoang", total.toString())
            Log.d("Testdoang1", harganya.toString())
        }

        if(total == 0){
            buttonCart.text = "----"
        } else{
            buttonCart.text = "Rp $total.000"
        }
    }
}