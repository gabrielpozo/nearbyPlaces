package com.gabriel.nearbyplaces

import android.app.Application
import com.gabriel.nearbyplaces.di.ApplicationComponent
import com.gabriel.nearbyplaces.di.DaggerApplicationComponent
import com.gabriel.nearbyplaces.di.modules.ApplicationModule

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */
class NearbyApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(
            ApplicationModule(this)
        ).build()
    }
}