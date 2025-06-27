package com.filmflare

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.gms.common.api.Status

class MapsActivity : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private var mGoogleMap: GoogleMap? = null
    private lateinit var autocompleteFragment: AutocompleteSupportFragment
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Hardcoded cinema locations
    private val cinemaLocations = listOf(

        //Durban
        LatLng(-29.8494444, 30.9361111),
        LatLng(-29.72592390000001, 31.0653207),
        LatLng(-29.8485997, 30.9991082),
        LatLng(-29.748665, 30.812639),
        LatLng(-30.033434, 30.899753),
        LatLng(-29.711307, 31.055982),
        LatLng(-29.525322, 31.203417),
        LatLng(-29.525322, 31.203417),

        // Cape Town
        LatLng(-33.904446, 18.419380),
        LatLng(-33.892070, 18.510983),
        LatLng(-33.873061, 18.634170),
        LatLng(-34.083721, 18.826673),

        //JHB
        LatLng(-26.146629, 28.041094),
        LatLng(-26.130707, 27.975878),
        LatLng(-26.146629, 28.041094),

    )

    companion object {
        private const val LOCATION_REQUEST_CODE = 1
        private const val RADIUS_IN_METERS = 20000 // 20 km radius
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Places API
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), getString(R.string.google_map_api_key))
        }

        autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setHint("Search Cinemas Near You")
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG))
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(status: Status) {}

            override fun onPlaceSelected(place: Place) {
                val latLng = place.latLng ?: return
                zoomOnMap(latLng)
                placeMarkerOnMap(latLng, place.address ?: "Selected Location", false) // Mark as non-cinema
            }
        })

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        mGoogleMap?.uiSettings?.isZoomControlsEnabled = true
        mGoogleMap?.uiSettings?.setAllGesturesEnabled(true)

        setUpMap()
    }

    private fun setUpMap() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
            return
        }

        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(requireContext(), "Please enable GPS", Toast.LENGTH_LONG).show()
            return
        }

        mGoogleMap?.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong, "Your Location", false) // User location
                mGoogleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))

                // Place markers for cinemas within 20 km
                placeNearbyCinemas(currentLatLong)
            } else {
                Toast.makeText(requireContext(), "Unable to get current location", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed to retrieve location", Toast.LENGTH_LONG).show()
        }
    }

    // Place markers for cinemas within the specified radius
    private fun placeNearbyCinemas(userLocation: LatLng) {
        val userLoc = Location("").apply {
            latitude = userLocation.latitude
            longitude = userLocation.longitude
        }

        var cinemaFound = false // Variable to track if a cinema is found

        for (cinema in cinemaLocations) {
            val cinemaLoc = Location("").apply {
                latitude = cinema.latitude
                longitude = cinema.longitude
            }

            // Calculate distance
            val distance = userLoc.distanceTo(cinemaLoc)
            if (distance <= RADIUS_IN_METERS) {
                placeMarkerOnMap(cinema, "Cinema Nearby", true) // Cinema marker
                cinemaFound = true // Mark that at least one cinema was found
            }
        }

        // If no cinemas were found within the radius, show a toast
        if (!cinemaFound) {
            Toast.makeText(requireContext(), "No cinemas found within 20 km radius", Toast.LENGTH_LONG).show()
        }
    }


    private fun placeMarkerOnMap(latLng: LatLng, title: String, isCinema: Boolean) {
        val markerOptions = MarkerOptions().position(latLng).title(title)

        // Use cinema icon if it's a cinema, otherwise the default user icon
        if (isCinema) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cinema_icon)) // Cinema icon
        } else {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker()) // Default Google Map marker
        }

        mGoogleMap?.addMarker(markerOptions)
    }

    private fun zoomOnMap(latLng: LatLng) {
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(latLng, 12f)
        mGoogleMap?.animateCamera(newLatLngZoom)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpMap()
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}
