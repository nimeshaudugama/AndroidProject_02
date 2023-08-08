package com.example.project_2



//
//import android.Manifest
//import android.app.Activity
//import android.content.ContentValues.TAG
//import android.content.Context
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.BitmapDescriptorFactory
//import com.google.android.gms.maps.model.GroundOverlayOptions
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import java.util.Locale
//
//class     GoogleMaps : Fragment(), OnMapReadyCallback {
//
//    private lateinit var mMap: GoogleMap
//    private val REQUEST_LOCATION_PERMISSION = 1
//    private var userLocation: LatLng? = null
//
//    private var currentLocationListener: OnCurrentLocationListener? = null
//
//    interface OnCurrentLocationListener {
//        fun onCurrentLocationReceived(location: LatLng)
//    }
//
//    // Function to set the current location listener
//    fun setCurrentLocationListener(listener: OnCurrentLocationListener?) {
//        currentLocationListener = listener
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_google_maps, container, false)
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//        Log.d(TAG, "GooglwMap")
//        return view
//
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker at a specific location.
//     */
//
//
//
//
//
//
////    private var currentLocationListener: OnCurrentLocationListener? = null
//
//    // ...
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        val zoomLevel = 15f
//
//        // Add a marker in London and move the camera
//        val london = LatLng(42.9849233, -81.245276) // London
//        mMap.addMarker(MarkerOptions().position(london).title("Marker in London"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, zoomLevel))
//        setMapLongClick(mMap)
//        setPoiClick(mMap)
//        val overlaySize = 100f
//        val androidOverlay = GroundOverlayOptions()
//            .image(BitmapDescriptorFactory.fromResource((R.drawable.android)))
//            .position(london, overlaySize)
//        mMap.addGroundOverlay(androidOverlay)
//        enableMyLocation(requireContext())
//
//        // Pass the current location to the parent activity/fragment
//        currentLocationListener?.onCurrentLocationReceived(london)
//        Log.d(TAG, "Current Location")
//    }
//
//    // Function to set the current location listener
////    fun setCurrentLocationListener(listener: OnCurrentLocationListener?) {
////        currentLocationListener = listener
////    }
//
//
//
//
//
//
//    private fun setMapLongClick(map:GoogleMap){
//        map.setOnMapLongClickListener { latLng ->
//            val snippet = String.format(
//                Locale.getDefault(),
//                "Lat: %1$.5f, Long: %2$.5f",
//                latLng.latitude,
//                latLng.longitude
//            )
//            map.addMarker(
//                MarkerOptions()
//                    .position(latLng)
//                    .title(getString(R.string.dropped_pin))
//                    .snippet(snippet)
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
//            )
//        }
//    }
//
//    private fun setPoiClick(map: GoogleMap){
//        map.setOnPoiClickListener { poi ->
//            val poiMarker = map.addMarker(
//                MarkerOptions()
//                    .position(poi.latLng)
//                    .title(poi.name)
//            )
//            poiMarker?.showInfoWindow()
//        }
//    }
//
////    private fun isPermissionGranted() : Boolean{
////        return ContextCompat.checkSelfPermission(
////            this,
////            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
////    }
//
//    private fun isPermissionGranted(context: Context): Boolean {
//        return ContextCompat.checkSelfPermission(
//            context,
//            android.Manifest.permission.ACCESS_COARSE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//
////    private fun enableMyLocation(){
////        if (isPermissionGranted()){
////            mMap.isMyLocationEnabled = true
////        }
////        else {
////            ActivityCompat.requestPermissions(
////                this,
////                arrayOf<String>(android.Manifest.permission.ACCESS_COARSE_LOCATION),
////                REQUEST_LOCATION_PERMISSION
////            )
////        }
////    }
//
//    private fun enableMyLocation(context: Context) {
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Request location permissions
//            ActivityCompat.requestPermissions(
//                context as Activity,
//                arrayOf(
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ),
//                REQUEST_LOCATION_PERMISSION
//            )
//        } else {
//            // Location permissions are already granted or have been requested
//            // Enable "My Location" on the map here
//
//
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
//                REQUEST_LOCATION_PERMISSION
//            )
//        }
//    }
//
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == REQUEST_LOCATION_PERMISSION){
//            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)){
//                enableMyLocation(requireContext())
//            }
//        }
//    }
//
////    private fun setMapStyle(map:GoogleMap){
////        try{
////            val success = map.setMapStyle(
////                MapStyleOptions.loadRawResourceStyle(
////                    this,
////                    R.raw.map_style
////                )
////            )
////            if (!success){
////                Log.e(TAG, "Style parsing failed.")
////            }
////        } catch (e: Resources.NotFoundException){
////            Log.e(TAG, "Can't find style. Error", e)
////        }
////    }
//}


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
                    null // Use the main looper for callbacks (can be any Looper if needed)
                )
            } catch (e: SecurityException) {
                // Handle the SecurityException here if the location permission is denied
                // You can show an error message or request location permissions again
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

                // Add a red marker at the current location
                mMap.clear() // Clear existing markers (optional)
                mMap.addMarker(MarkerOptions().position(currentLocation!!).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))

                // Stop requesting location updates after the first result (optional)
                fusedLocationClient.removeLocationUpdates(this)
            } else {
                // Handle the case when the location is null
                // You can show an error message or retry requesting location updates
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

                    // Create a FindCurrentPlaceRequest and fetch the current place
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
            // Handle the case where the location permission is not granted
            Log.e("Nearby Places", "Location permission not granted")
        }


}

     fun showNearbyPlacesInGooglePlacesFragment(nearbyPlaces: List<Place>) {
        // Create or get the instance of GooglePlacesFragment
        val googlePlacesFragment = GooglePlaces.newInstance(nearbyPlaces)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, googlePlacesFragment, "GooglePlacesFragment")
            .commit()
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
                // Permission granted, enable my location and request location updates
                enableMyLocation()
            } else {
                // Permission denied, handle this case appropriately
                // You can show an error message or disable location-related functionality
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
            // Show an explanation to the user here if needed
            // You can show a dialog explaining why location permission is necessary

            // Then, request location permissions again
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

