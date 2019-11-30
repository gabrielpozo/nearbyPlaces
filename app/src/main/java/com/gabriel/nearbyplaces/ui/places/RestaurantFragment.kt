package com.gabriel.nearbyplaces.ui.places

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gabriel.data.source.remote.location.RegionRepository
import com.gabriel.nearbyplaces.R
import com.gabriel.nearbyplaces.utils.PermissionRequester
import com.gabriel.presentation.di.ViewModelProviderFactory
import com.gabriel.presentation.viewmodels.NearbyViewModel
import com.gabriel.presentation.viewmodels.NearbyViewModel.*
import javax.inject.Inject

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */
class RestaurantFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    @Inject
    lateinit var coarsePermissionRequester: PermissionRequester
    @Inject
    lateinit var regionRepository: RegionRepository

    private val viewModel by lazy {
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
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresentationComponent().inject(this)
        observePlaces()
    }

    private fun observePlaces() {
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUI))
    }


    private fun updateUI(model: UiModel) {
        when (model) {
            is UiModel.Loading -> {

            }
            is UiModel.Content -> {

            }
            is UiModel.GetLocation -> {
                regionRepository.findLastLocation(::handleResultLocation)
            }
            is UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }


    private fun handleResultLocation(location: String) {
        viewModel.getPlaceList(location)
    }
}