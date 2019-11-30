package com.gabriel.nearbyplaces.di.subcomponents

import com.gabriel.nearbyplaces.di.modules.NearbyPlacesModule
import com.gabriel.nearbyplaces.di.modules.NearbyPlacesScope
import com.gabriel.nearbyplaces.ui.places.RestaurantFragment
import com.gabriel.presentation.di.modules.ViewModelModule
import dagger.Subcomponent

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */
@NearbyPlacesScope
@Subcomponent(modules = [ViewModelModule::class, NearbyPlacesModule::class])
interface PresentationComponent {
    fun inject(baseNearbyPlacesFragment: RestaurantFragment)
}
