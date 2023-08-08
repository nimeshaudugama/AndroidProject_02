package com.example.project_2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_2.GoogleMaps
import com.example.project_2.PlacesAdapter
import com.example.project_2.R
import com.google.android.libraries.places.api.model.Place

class GooglePlaces : Fragment() {

    private lateinit var placesAdapter: PlacesAdapter
    private var nearbyPlacesList: List<Place>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_google_places, container, false)

        // Retrieve the nearby places data from arguments bundle
        val places: List<Place>? = arguments?.getParcelableArrayList(KEY_NEARBY_PLACES)

        // Set up RecyclerView and adapter
        val recyclerView: RecyclerView = view.findViewById(R.id.placesRecyclerView)
        placesAdapter = PlacesAdapter(places ?: emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = placesAdapter

        return view
    }

    fun setNearbyPlaces(placesList: List<Place>) {
        nearbyPlacesList = placesList
    }

    companion object {
        private const val KEY_NEARBY_PLACES = "KEY_NEARBY_PLACES"

        fun newInstance(places: List<Place>): GooglePlaces {
            val fragment = GooglePlaces()
            val args = Bundle().apply {
                putParcelableArrayList(KEY_NEARBY_PLACES, ArrayList(places))
            }
            fragment.arguments = args
            return fragment
        }
    }
}