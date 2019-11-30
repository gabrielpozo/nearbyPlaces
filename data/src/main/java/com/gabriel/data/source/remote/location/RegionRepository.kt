package com.gabriel.data.source.remote.location


/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
class RegionRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    fun findLastLocation(location: (String) -> Unit) {
        if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.findCurrentLocation { location ->
                location(location)
            }
        }
    }
}

interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    fun check(permission: Permission): Boolean
}
