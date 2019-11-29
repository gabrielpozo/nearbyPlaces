package com.gabriel.nearbyplaces.ui.places

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gabriel.nearbyplaces.R

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */
class RestaurantFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("Gabriel","onCreateView here Restaurant")
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }
}