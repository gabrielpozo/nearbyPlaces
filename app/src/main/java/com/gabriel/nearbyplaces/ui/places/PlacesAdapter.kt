package com.gabriel.nearbyplaces.ui.places

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.domain.models.Place
import com.gabriel.nearbyplaces.R
import com.gabriel.nearbyplaces.ui.places.common.basicDiffUtil
import com.gabriel.nearbyplaces.ui.places.common.inflate
import kotlinx.android.synthetic.main.view_place.view.*

/**
 * Created by Gabriel Pozo Guzman on 2019-12-01.
 */

class PlacesAdapter :
    RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    var places: List<Place> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.name == new.name }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_place, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(place: Place) {
            itemView.apply {
                place_name.text = place.name
                distance.text =
                    resources.getString(R.string.distance_metres, place.distance.toString())
                opening_hours.text = place.openingHours
                rating.text = place.rating
                itemView.resources.getString(R.string.open_now)
                if (place.openingHours == resources.getString(R.string.open_now)) {
                    itemView.opening_hours.setTextColor(resources.getColor(R.color.openGreen))
                } else {
                    itemView.opening_hours.setTextColor(resources.getColor(R.color.red))
                }
            }
        }
    }
}

