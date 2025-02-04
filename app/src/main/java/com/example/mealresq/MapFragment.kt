@file:Suppress("DEPRECATION", "SameParameterValue")

package com.example.mealresq

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.bottomsheet.view.*

@Suppress("DEPRECATION")
class MapFragment: Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var rootView: View
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var locationCallback: LocationCallback

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private lateinit var markerBurger: Marker
    private lateinit var markerSate: Marker
    private lateinit var clickedRestoran: Restaurant

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        rootView = inflater.inflate(R.layout.activity_maps, container, false)
        setHasOptionsMenu(true)

        inisialisasiLokasi()
        inisialisasiBS()
        inisialisasiAutoComplete()

        clickedRestoran = Restaurant()

        return rootView
    }

    companion object{
        private const val ARG_POSITION: String = "position"
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
        fun newInstance(): MapFragment{
            val fragment = MapFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMapToolbarEnabled = false
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
        placeMarker()
        map.setOnMarkerClickListener(this)

        setUpMap()

        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(activity as AppCompatActivity) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        myRef.child("restoran").child(marker!!.title)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
//                    clickedRestoran = Restaurant()
                    clickedRestoran.name = snapshot.child("namarestoran").value.toString()
                    clickedRestoran.photo = snapshot.child("fotorestoran").value.toString()
                    clickedRestoran.rating = snapshot.child("rating").value.toString()

                    changeBottomsheet(clickedRestoran)
                }
            })



        Toast.makeText(context, "${marker.title} clicked", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(rootView.context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity as AppCompatActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(activity as AppCompatActivity) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    //inisialisasi lokasi dari pengguna
    private fun inisialisasiLokasi(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                // TODO: last locationnya
            }
        }
    }

    private fun inisialisasiBS(){
        bottomSheetBehavior = BottomSheetBehavior.from(rootView.bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // handle onSlide
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
////                    BottomSheetBehavior.STATE_COLLAPSED -> Toast.makeText(activity, "STATE_COLLAPSED", Toast.LENGTH_SHORT).show()
////                    BottomSheetBehavior.STATE_EXPANDED -> Toast.makeText(activity, "STATE_EXPANDED", Toast.LENGTH_SHORT).show()
////                    BottomSheetBehavior.STATE_DRAGGING -> Toast.makeText(activity, "STATE_DRAGGING", Toast.LENGTH_SHORT).show()
////                    BottomSheetBehavior.STATE_SETTLING -> Toast.makeText(activity, "STATE_SETTLING", Toast.LENGTH_SHORT).show()
////                    BottomSheetBehavior.STATE_HIDDEN -> Toast.makeText(activity, "STATE_HIDDEN", Toast.LENGTH_SHORT).show()
////                    else -> Toast.makeText(activity, "OTHER_STATE", Toast.LENGTH_SHORT).show()
//                }
            }
        })
    }

    private fun inisialisasiAutoComplete(){
        if (!Places.isInitialized()) {
            Places.initialize(context!!, getString(R.string.google_maps_key))
        }

        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.place_autocomplete) as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i("TEMPAT", "Place: ${place.name}, ${place.id}")
            }

            override fun onError(status: Status) {
                Log.i("TEMPAT", "An error occurred: $status")
            }
        })
    }

    private fun placeMarker(){
        val first = LatLng(-6.261581, 106.673136)
        val second = LatLng(-6.260740, 106.671381)

        markerBurger = map.addMarker(
            MarkerOptions()
                .position(first)
                .title("Burger Bang Deni")
        )

        markerSate = map.addMarker(
            MarkerOptions()
                .position(second)
                .title("Roti Bakar Bang Ali")
        )
    }

    private fun changeBottomsheet(restoran: Restaurant){
        val imagenya = rootView.findViewById<CircleImageView>(R.id.fotorestobs)
        val namanya = rootView.findViewById<TextView>(R.id.namabs)

        Picasso.get().load(restoran.photo).into(imagenya)
        namanya.text = restoran.name

        imagenya.setOnClickListener {
            val intent = Intent(activity, RestoranActivity::class.java)
            val str: String = restoran.name
            intent.putExtra(RestoranActivity.STRINGNYA, str)
            startActivity(intent)
        }
    }
}