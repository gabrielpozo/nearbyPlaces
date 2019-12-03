package com.gabriel.nearbyplaces.di.subcomponents

import com.gabriel.nearbyplaces.di.modules.NearbyPlacesModule
import com.gabriel.nearbyplaces.di.modules.NearbyPlacesScope
import com.gabriel.nearbyplaces.ui.places.bars.BarFragment
import com.gabriel.nearbyplaces.ui.places.cafes.CafeFragment
import com.gabriel.nearbyplaces.ui.places.restaurants.RestaurantFragment
import com.gabriel.presentation.di.modules.ViewModelModule
import dagger.Subcomponent

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */

@NearbyPlacesScope
@Subcomponent(modules = [ViewModelModule::class, NearbyPlacesModule::class])
interface PresentationComponent {
    fun inject(restaurantFragment: RestaurantFragment)
    fun inject(barFragment: BarFragment)
    fun inject(cafeFragment: CafeFragment)
}
