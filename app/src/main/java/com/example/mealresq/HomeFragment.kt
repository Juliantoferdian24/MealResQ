package com.example.mealresq

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class HomeFragment: Fragment() {

    private lateinit var rootView: View

    private lateinit var rvRestoran: RecyclerView
    private lateinit var rvRestoranNearMe: RecyclerView
    private var list: ArrayList<Restaurant> = arrayListOf()
    private lateinit var svRestaurant: androidx.appcompat.widget.SearchView

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference
    private lateinit var adapter: ListRestaurantAdapter

    private lateinit var iconCart: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        rootView = inflater.inflate(R.layout.home_fragment, container, false)
        setHasOptionsMenu(true)

        rvRestoran = rootView.findViewById(R.id.rv_restoran)
        rvRestoranNearMe = rootView.findViewById(R.id.rv_restoran2)
        rvRestoran.setHasFixedSize(true)
        rvRestoranNearMe.setHasFixedSize(true)

//        list.addAll(RestoranData.listData)
        svRestaurant = rootView.findViewById(R.id.searchview_restoran)
        svRestaurant.setQuery("", false)

        iconCart = rootView.findViewById(R.id.gambar_cart)
        iconCart.setOnClickListener {
            startActivity(Intent(activity, CartActivity::class.java))
        }

        rootView.requestFocus()

        showRecyclerList()
        addFromFirebase(list)
        return rootView
    }

    private fun showRecyclerList() {
        rvRestoran.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRestoranNearMe.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = ListRestaurantAdapter(list)
        rvRestoran.adapter = adapter
        rvRestoranNearMe.adapter = adapter

        adapter.setOnItemClickCallBack(object: ListRestaurantAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Restaurant) {
                val intent = Intent(activity, RestoranActivity::class.java)
                val str: String = data.name
                intent.putExtra(RestoranActivity.STRINGNYA, str)
                startActivity(intent)
//                Toast.makeText(context, data.name, Toast.LENGTH_SHORT).show()
            }
        })

        svRestaurant.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
    }

    private fun addFromFirebase(list: ArrayList<Restaurant>){
        myRef.child("restoran")
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()

                    for (key in snapshot.children){
                        val restoran = Restaurant()

                        restoran.name = key.child("namarestoran").value.toString()
                        restoran.rating = key.child("rating").value.toString()
                        restoran.photo = key.child("fotorestoran").value.toString()

                        list.add(restoran)
                    }
                    adapter.notifyDataSetChanged()
                }

            })
    }

    companion object{
//        var TAG = ProfileFragment::class.java.simpleName
        private const val ARG_POSITION: String = "position"
        fun newInstance(): HomeFragment{
            val fragment = HomeFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }
}