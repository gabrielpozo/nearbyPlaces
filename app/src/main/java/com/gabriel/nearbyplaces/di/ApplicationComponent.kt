package com.gabriel.nearbyplaces.di

import com.gabriel.nearbyplaces.di.subcomponents.PresentationComponent
import com.gabriel.nearbyplaces.di.modules.ApplicationModule
import com.gabriel.nearbyplaces.di.modules.NearbyPlacesModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun presentationComponent(nearbyPlacesModule: NearbyPlacesModule): PresentationComponent
}