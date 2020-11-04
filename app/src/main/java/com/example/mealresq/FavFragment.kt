package com.example.mealresq

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavFragment: Fragment() {

    private lateinit var rootView: View
    private lateinit var rvFav: RecyclerView
    private var list: ArrayList<Restaurant> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        rootView = inflater.inflate(R.layout.fav_fragment, container, false)
        setHasOptionsMenu(true)

        rvFav = rootView.findViewById(R.id.rv_fav)
        rvFav.setHasFixedSize(true)

        list.addAll(RestoranData.listData)
        showRecyclerList()

        return rootView
    }

    private fun showRecyclerList() {
        rvFav.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val listRestoranAdapter = ListRestaurantAdapter(list)
        rvFav.adapter = listRestoranAdapter


        listRestoranAdapter.setOnItemClickCallBack(object: ListRestaurantAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Restaurant) {
                val intent = Intent(activity, RestoranActivity::class.java)
                startActivity(intent)
                Toast.makeText(context, data.name, Toast.LENGTH_SHORT).show()
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