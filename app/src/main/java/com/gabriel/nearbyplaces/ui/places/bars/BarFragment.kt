package com.gabriel.nearbyplaces.ui.places.bars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gabriel.data.source.remote.location.RegionRepository
import com.gabriel.nearbyplaces.R
import com.gabriel.nearbyplaces.ui.BaseFragment
import com.gabriel.nearbyplaces.ui.places.PlacesAdapter
import com.gabriel.nearbyplaces.utils.PermissionRequester
import com.gabriel.presentation.di.ViewModelProviderFactory
import com.gabriel.presentation.viewmodels.BarViewModel
import com.gabriel.presentation.viewmodels.BarViewModel.*
import kotlinx.android.synthetic.main.fragment_restaurant.*
import javax.inject.Inject

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */

class BarFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    @Inject
    lateinit var coarsePermissionRequester: PermissionRequester
    @Inject
    lateinit var regionRepository: RegionRepository
    private lateinit var adapter: PlacesAdapter
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(BarViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresentationComponent().inject(this)
        adapter = PlacesAdapter()
        recycler.adapter = adapter
        observePlaces()
    }

    private fun observePlaces() {
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUI))
    }

    private fun updateUI(model: UiModel) {
        progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE
        when (model) {
            is UiModel.Content -> adapter.places = model.places
            is UiModel.GetLocation -> regionRepository.findLastLocation(::handleResultLocation)
            is UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }

    private fun handleResultLocation(location: String) {
        viewModel.getPlaceList(location)
    }
}