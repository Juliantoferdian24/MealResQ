package com.example.mealresq

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FavFragment: Fragment() {

    private lateinit var rootView: View
    private lateinit var hargaReal: TextView

    private lateinit var rvFavorite: RecyclerView
    private var list: ArrayList<Restaurant> = arrayListOf()

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference
    private lateinit var adapter: FavoriteAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        rootView = inflater.inflate(R.layout.fav_fragment, container, false)
        setHasOptionsMenu(true)

        auth = FirebaseAuth.getInstance()

        rvFavorite = rootView.findViewById(R.id.rv_fav)

        showRecyclerList()
        addFromFirebase(list)

//        hargaReal = rootView.findViewById(R.id.hargaReal)
//        hargaReal.paintFlags = hargaReal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//        hargaReal.text = "Rp. 20.000,00"

        return rootView
    }

    private fun showRecyclerList() {
        rvFavorite.layoutManager = LinearLayoutManager(context)
        adapter = FavoriteAdapter(list)
        rvFavorite.adapter = adapter

        adapter.setOnItemClickCallBack(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Restaurant) {
                Toast.makeText(context, "CLICKED", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addFromFirebase(list: ArrayList<Restaurant>){
        myRef.child("Users").child(auth.currentUser!!.uid).child("favorites")
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()

                    for (key in snapshot.children){
                        val restoran = Restaurant()

                        restoran.name = key.child("namarestoran").value.toString()
                        restoran.rating = "4.0"
                        restoran.photo = key.child("fotonya").value.toString()

                        list.add(restoran)
                    }
                    adapter.notifyDataSetChanged()
                }

            })
    }

    companion object{
        //        var TAG = ProfileFragment::class.java.simpleName
        private const val ARG_POSITION: String = "position"
        fun newInstance(): FavFragment{
            val fragment = FavFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }

}