package com.example.mealresq

import android.Manifest
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
import kotlinx.android.synthetic.main.alert_dialog.view.*


class RestoranActivity: AppCompatActivity() {

    private lateinit var rvMenu: RecyclerView
    private var list: ArrayList<Menu> = arrayListOf()
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var fotoRestoran: ImageView
    private lateinit var phone: ImageView

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


        super.onCreate(savedInstanceState)
    }

    private fun showRecyclerList() {
        rvMenu.layoutManager = LinearLayoutManager(this)
        val listRestoranAdapter = MenuRestaurantAdapter(list)
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
}