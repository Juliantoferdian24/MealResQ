package com.example.mealresq

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment: Fragment() {

    private lateinit var rootView: View

    private lateinit var rvRestoran: RecyclerView
    private lateinit var rvRestoranNearMe: RecyclerView
    private var list: ArrayList<Restaurant> = arrayListOf()
    private lateinit var svRestaurant: androidx.appcompat.widget.SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        rootView = inflater.inflate(R.layout.home_fragment, container, false)
        setHasOptionsMenu(true)

        rvRestoran = rootView.findViewById(R.id.rv_restoran)
        rvRestoranNearMe = rootView.findViewById(R.id.rv_restoran2)
        rvRestoran.setHasFixedSize(true)
        rvRestoranNearMe.setHasFixedSize(true)

        list.addAll(RestoranData.listData)
        svRestaurant = rootView.findViewById(R.id.searchview_restoran)
        showRecyclerList()


        return rootView
    }

    private fun showRecyclerList() {
        rvRestoran.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRestoranNearMe.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val listRestoranAdapter = ListRestaurantAdapter(list)
        rvRestoran.adapter = listRestoranAdapter
        rvRestoranNearMe.adapter = listRestoranAdapter

        listRestoranAdapter.setOnItemClickCallBack(object: ListRestaurantAdapter.OnItemClickCallback {
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
                listRestoranAdapter.filter.filter(newText)
                return false
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