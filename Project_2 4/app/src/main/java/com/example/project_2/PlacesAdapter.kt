package com.example.project_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.model.Place

class PlacesAdapter(private var placesList: List<Place>) :
    RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.place_list, parent, false)
        return PlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = placesList[position]
        holder.bind(place)
    }

    override fun getItemCount(): Int {
        return placesList.size
    }

    fun setData(newPlacesList: List<Place>) {
        placesList = newPlacesList
        notifyDataSetChanged()
    }

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeNameTextView: TextView = itemView.findViewById(R.id.textViewName)
        private val placeAddressTextView: TextView = itemView.findViewById(R.id.textViewAddress)

        fun bind(place: Place) {
            placeNameTextView.text = place.name
            placeAddressTextView.text = place.address
        }
    }
}
