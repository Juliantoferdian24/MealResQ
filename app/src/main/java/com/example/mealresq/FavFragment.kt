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

class FavFragment: Fragment() {

    private lateinit var rootView: View
    private lateinit var hargaReal: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        rootView = inflater.inflate(R.layout.fav_fragment, container, false)
        setHasOptionsMenu(true)

        hargaReal = rootView.findViewById(R.id.hargaReal)
        hargaReal.paintFlags = hargaReal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        hargaReal.text = "Rp. 20.000,00"

        return rootView
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