package com.example.mealresq

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment


class ProfileFragment: Fragment(){
    private lateinit var rootView: View
    private lateinit var mgoToCommunity: ImageButton
    private lateinit var mgoToAboutUs: ImageButton
    private lateinit var tgoToCommunity: TextView
    private lateinit var tgoToAboutUs: TextView
    private lateinit var tgoToLogOut: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.profile_fragment, container, false)
        setHasOptionsMenu(true)
        mgoToCommunity = rootView.findViewById(R.id.goToCommunity)
        mgoToAboutUs = rootView.findViewById(R.id.goToLogout)

        tgoToCommunity = rootView.findViewById(R.id.joinOurCommunity)
        tgoToAboutUs = rootView.findViewById(R.id.aboutUs)
        tgoToLogOut = rootView.findViewById(R.id.logout)
        mgoToCommunity.setOnClickListener {
            val intent = Intent(context, CommunityActivity::class.java)
            startActivity(intent)
        }
        mgoToAboutUs.setOnClickListener {
            val intent = Intent(context, AboutActivity::class.java)
            startActivity(intent)
        }

        tgoToCommunity.setOnClickListener {
            val intent = Intent(context, CommunityActivity::class.java)
            startActivity(intent)
        }
        tgoToAboutUs.setOnClickListener {
            val intent = Intent(context, AboutActivity::class.java)
            startActivity(intent)
        }
        tgoToLogOut.setOnClickListener(View.OnClickListener {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString("remember", "false")
            editor.apply()

            val i = Intent(context, GettingStarted::class.java)
            startActivity(i)
        })


        return rootView
    }


    companion object {
        //        var TAG = ProfileFragment::class.java.simpleName
        private const val ARG_POSITION: String = "position"
        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }
}
