package com.example.project_2




import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place

class MainActivity : AppCompatActivity(), GoogleMaps.OnCurrentLocationListener, GoogleMaps.NearbyPlacesListener {

    private lateinit var placesClient: PlacesClient
    private lateinit var googleMapsFragment: GoogleMaps

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Places.initialize(applicationContext, "YOUR_API_KEY")
        placesClient = Places.createClient(this)

        googleMapsFragment = GoogleMaps()

        val btnMap = findViewById<Button>(R.id.btnMap)
        btnMap.setOnClickListener {
            replaceFragment(googleMapsFragment)
        }

        val btnPlaces = findViewById<Button>(R.id.btnPlaces)
        btnPlaces.setOnClickListener {
            val exampleFragment = GooglePlaces()
            googleMapsFragment.fetchNearbyPlaces(placesClient)
            replaceFragment(exampleFragment)
        }

        val btnEmail = findViewById<Button>(R.id.btnEmail)
        btnEmail.setOnClickListener {
            val exampleFragment = Email()
            replaceFragment(exampleFragment)
        }

        val btnAbout = findViewById<Button>(R.id.btnAbout)
        btnAbout.setOnClickListener {
            val exampleFragment = About()
            replaceFragment(exampleFragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onNearbyPlacesFetched(nearbyPlaces: List<Place>) {
        val googlePlacesFragment = GooglePlaces.newInstance(nearbyPlaces)
        replaceFragment(googlePlacesFragment)
    }

    override fun onCurrentLocationReceived(location: LatLng) {
        val googlePlacesFragment = GooglePlaces()
        val bundle = Bundle()
        bundle.putParcelable("location", location)
        googlePlacesFragment.arguments = bundle

        val fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, googlePlacesFragment, "GooglePlacesFragmentTag")
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun getPlacesClient(): PlacesClient {
        return placesClient
    }
}
