package com.example.mealresq

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.profile_fragment.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton


class ProfileFragment: Fragment(){
    private lateinit var imagetest: ImageView
    private lateinit var imageref: StorageReference
    private lateinit var rootView: View
    private lateinit var mgoToCommunity: ImageButton
    private lateinit var mgoToAboutUs: ImageButton
    private lateinit var mgoToLogOut : ImageButton
    private lateinit var tgoToCommunity: TextView
    private lateinit var tgoToAboutUs: TextView
    private lateinit var tgoToLogOut: TextView
    private lateinit var tgotoEditProfile: TextView

    private lateinit var name: TextView
    private lateinit var address: TextView

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
        tgotoEditProfile = rootView.findViewById(R.id.editProfile)

        name = rootView.findViewById(R.id.tv_name)
        address = rootView.findViewById(R.id.tv_address)

        imageref = FirebaseStorage.getInstance().reference.child("profileImage/${FirebaseAuth.getInstance().currentUser!!.uid}")
        imageref.downloadUrl.addOnSuccessListener { Uri->
            val imageURL = Uri.toString()
            imagetest = rootView.findViewById(R.id.fotoProfil)

            Glide.with(this)
                .load(imageURL)
                .circleCrop()
                .into(imagetest)
        }
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

        tgotoEditProfile.setOnClickListener{
            val intent = Intent(activity, EditProfileData::class.java)
            startActivity(intent)
        }
        val acct = GoogleSignIn.getLastSignedInAccount(activity)
        if (acct != null){
            name.text = acct.displayName
            address.text = acct.email
        } else{
            // Write a message to the database
            // Write a message to the database
            val database = FirebaseDatabase.getInstance()
            val myRef = database.reference
            // Read from the database
            myRef.child("Users")
                .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    name.text = dataSnapshot.child("${FirebaseAuth.getInstance().currentUser!!.uid}/nama").value.toString()
                    address.text = dataSnapshot.child("${FirebaseAuth.getInstance().currentUser!!.uid}/address").value.toString()
                }


                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(activity, "failed", Toast.LENGTH_SHORT).show()
                }
            })
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
