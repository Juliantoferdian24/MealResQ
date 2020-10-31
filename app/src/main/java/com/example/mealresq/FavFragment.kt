package com.example.mealresq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FavFragment: Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        rootView = inflater.inflate(R.layout.fav_fragment, container, false)
        setHasOptionsMenu(true)

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