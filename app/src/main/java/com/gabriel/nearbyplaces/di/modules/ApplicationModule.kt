package com.gabriel.nearbyplaces.di.modules

import android.content.Context
import com.gabriel.nearbyplaces.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Gabriel Pozo Guzman on 2019-11-29.
 */
@Module
class ApplicationModule(val context: Context) {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}