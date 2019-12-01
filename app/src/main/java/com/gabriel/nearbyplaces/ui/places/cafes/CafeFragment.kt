package com.gabriel.nearbyplaces.ui.places.cafes

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
class CafeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("Gabriel","onCreateView here Cafe")
        return inflater.inflate(R.layout.fragment_cafe, container, false)
    }
}