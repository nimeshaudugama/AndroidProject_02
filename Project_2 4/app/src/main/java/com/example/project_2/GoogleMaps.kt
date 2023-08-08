package com.example.project_2





import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.project_2.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient

class GoogleMaps : Fragment(), OnMapReadyCallback{

    interface NearbyPlacesListener {
        fun onNearbyPlacesFetched(nearbyPlaces: List<Place>)
    }

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var currentLocation: LatLng? = null
    private var currentLocationListener: OnCurrentLocationListener? = null
    private lateinit var nearbyPlacesListener: NearbyPlacesListener

    interface OnCurrentLocationListener {
        fun onCurrentLocationReceived(location: LatLng)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NearbyPlacesListener) {
            nearbyPlacesListener = context
        } else {
            throw ClassCastException("$context must implement NearbyPlacesListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_google_maps, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Places API client
        Places.initialize(requireContext(), "AIzaSyAnYTe2LVCEKH-MjYOUyOpWeCaIG4T9BS4")
        val placesClient: PlacesClient = Places.createClient(requireContext())


        // Fetch nearby places and print them to the console
        fetchNearbyPlaces(placesClient)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Check for location permissions and enable the My Location button
        if (isLocationPermissionGranted()) {
            enableMyLocation()
        } else {
            // Request location permissions
            requestLocationPermissions()
        }
    }

    private fun enableMyLocation() {
        if (isLocationPermissionGranted()) {
            try {
                mMap.isMyLocationEnabled = true
                val locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000) // Set the interval to get location updates (in milliseconds)

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null
                )
            } catch (e: SecurityException) {

                requestLocationPermissions()
            }
        } else {
            // Request location permissions
            requestLocationPermissions()
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }



    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            if (location != null) {
                currentLocation = LatLng(location.latitude, location.longitude)
                currentLocationListener?.onCurrentLocationReceived(currentLocation!!)
                mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        currentLocation!!,
                        DEFAULT_ZOOM.toFloat()
                    )
                )


                mMap.clear()
                mMap.addMarker(MarkerOptions().position(currentLocation!!).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))


                fusedLocationClient.removeLocationUpdates(this)
            } else {

            }
        }
    }





    fun fetchNearbyPlaces(placesClient: PlacesClient) {
        // Check if the required permissions are granted
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Get the last known location of the device
            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                // If the location is not null, continue with finding nearby places
                location?.let {
                    // Set the fields to specify which types of place data to return
                    val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS)


                    val request = FindCurrentPlaceRequest.builder(placeFields).build()
                    placesClient.findCurrentPlace(request)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Get the result of the findCurrentPlace request
                                val response = task.result as FindCurrentPlaceResponse

//                                // Retrieve the nearby places and pass them to GooglePlacesFragment
                                val nearbyPlaces = response.placeLikelihoods.map { it.place }
                                showNearbyPlacesInGooglePlacesFragment(nearbyPlaces)

                                // Retrieve the nearby places and pass them to MainActivity
//                                val nearbyPlaces = response.placeLikelihoods.map { it.place }
//                                nearbyPlacesListener.onNearbyPlacesFetched(nearbyPlaces)
                            } else {
                                // Handle errors here if the findCurrentPlace request fails
                                Log.e("Nearby Places", "Exception: ${task.exception}")
                            }
                        }
                }
            }
        } else {

            Log.e("Nearby Places", "Location permission not granted")
        }


}

     fun showNearbyPlacesInGooglePlacesFragment(nearbyPlaces: List<Place>) {

        val googlePlacesFragment = GooglePlaces.newInstance(nearbyPlaces)
       requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, googlePlacesFragment, "GooglePlacesFragment")

           .commit()

//         requireActivity().supportFragmentManager.beginTransaction()
//             .add(R.id.fragmentContainerView, googlePlacesFragment, "GooglePlacesFragment")
//             .addToBackStack(null)
//             .commit()


    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            // Check if the user granted the location permissions
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                enableMyLocation()
            } else {

            }
        }
    }


    private fun requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
        } else {

        }
    }








    fun setCurrentLocationListener(listener: OnCurrentLocationListener) {
        currentLocationListener = listener
    }

    companion object {
        private const val DEFAULT_ZOOM = 15
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}

