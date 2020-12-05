package com.example.mealresq

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton


class ProfileFragment: Fragment(){
    private lateinit var rootView: View
    private lateinit var mgoToCommunity: ImageButton
    private lateinit var mgoToAboutUs: ImageButton
    private lateinit var mgoToLogOut : ImageButton
    private lateinit var tgoToCommunity: TextView
    private lateinit var tgoToAboutUs: TextView
    private lateinit var tgoToLogOut: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.profile_fragment, container, false)
        setHasOptionsMenu(true)



        mgoToCommunity = rootView.findViewById(R.id.goToCommunity)
        mgoToAboutUs = rootView.findViewById(R.id.goToAboutUs)
        mgoToLogOut = rootView.findViewById(R.id.goToLogout)

        tgoToCommunity = rootView.findViewById(R.id.joinOurCommunity)
        tgoToAboutUs = rootView.findViewById(R.id.aboutUs)
        tgoToLogOut = rootView.findViewById(R.id.logout)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity!!, gso)
        auth = FirebaseAuth.getInstance()

        mgoToCommunity.setOnClickListener {
            val intent = Intent(context, CommunityActivity::class.java)
            startActivity(intent)
        }
        mgoToAboutUs.setOnClickListener {
            val intent = Intent(context, AboutActivity::class.java)
            startActivity(intent)
        }
        mgoToLogOut.setOnClickListener {
            alert("Are you sure want to logout?"){
                noButton {  }
                yesButton {
                    auth.signOut()
                    googleSignInClient.signOut()
                    val intent = Intent(activity, GettingStarted::class.java)
                    startActivity(intent)
                }
            }.show()
        }

        tgoToCommunity.setOnClickListener {
            val intent = Intent(context, CommunityActivity::class.java)
            startActivity(intent)
        }
        tgoToAboutUs.setOnClickListener {
            val intent = Intent(context, AboutActivity::class.java)
            startActivity(intent)
        }

        tgoToLogOut.setOnClickListener {
            alert("Are you sure want to logout?"){
                noButton {  }
                yesButton {
                    auth.signOut()
                    googleSignInClient.signOut()
                    val intent = Intent(activity, GettingStarted::class.java)
                    startActivity(intent)
                }
            }.show()

        }
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
