package com.gabriel.nearbyplaces.utils

import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
class PermissionRequester(private val activity: Activity, private val permission: String) {
    fun request(continuation: (Boolean) -> Unit) {
        Dexter
            .withActivity(activity)
            .withPermission(permission)
            .withListener(object : BasePermissionListener() {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    continuation(true)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    continuation(false)
                }
            }
            ).check()
    }
}