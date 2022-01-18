package com.example.androidproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androidproject.database.DatabaseLocalisation
import com.example.androidproject.viewModel.ListLocalisationViewModel
import com.example.androidproject.viewModel.LocalisationViewModel
import com.example.androidproject.viewModelFactory.ListLocalisationViewModelFactory
import com.example.androidproject.viewModelFactory.LocalisationViewModelFactory
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: LocalisationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args = MapFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dataSource = DatabaseLocalisation.getInstance(application).localisationDao
        val viewModelFactory = LocalisationViewModelFactory(dataSource, application, args.localisationId)

        viewModel = ViewModelProvider(this, viewModelFactory).get(LocalisationViewModel::class.java)
        var rootView = inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        var latitude = viewModel.localisation.value?.latitude

        var longitude = viewModel.localisation.value?.longitude

        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            var center: CameraUpdate

            if (latitude != null && longitude != null) {
                val marker = LatLng(latitude.toDouble(), longitude.toDouble())
                center = CameraUpdateFactory.newLatLng(marker)
                var zoom: CameraUpdate
                zoom = CameraUpdateFactory.zoomTo(18F)
                mMap.addMarker(
                    MarkerOptions().position(marker).title("Localisation de votre montre")
                )
                mMap.moveCamera(center)
                mMap.animateCamera(zoom)

            }
        }
        return rootView
    }
}