package com.gabriel.nearbyplaces.ui.places

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gabriel.nearbyplaces.R
import com.gabriel.presentation.di.ViewModelProviderFactory
import com.gabriel.presentation.viewmodels.NearbyViewModel
import javax.inject.Inject

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */
class RestaurantFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    protected val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(NearbyViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("Gabriel", "onCreateView here Restaurant")
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresentationComponent().inject(this)
        updateUI()
    }


    private fun updateUI() {
        viewModel.onCoarsePermissionRequested()
    }
}