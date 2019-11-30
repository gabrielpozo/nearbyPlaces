package com.gabriel.data.source.remote.location

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
class AndroidPermissionChecker(private val application: Application) :
    PermissionChecker {

    override fun check(permission: PermissionChecker.Permission): Boolean =
        ContextCompat.checkSelfPermission(
            application,
            permission.toAndroidId()
        ) == PackageManager.PERMISSION_GRANTED

    private fun PermissionChecker.Permission.toAndroidId() = when (this) {
        PermissionChecker.Permission.FINE_LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
    }
}