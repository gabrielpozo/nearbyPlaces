package com.gabriel.nearbyplaces.di.subcomponents

import com.gabriel.nearbyplaces.di.modules.NearbyPlacesScope
import dagger.Subcomponent

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */
@NearbyPlacesScope
@Subcomponent(modules = [])
interface PresentationComponent {
    //fun inject(baseNearbyPlacesFragment: BaseNearbyFragment)
}
