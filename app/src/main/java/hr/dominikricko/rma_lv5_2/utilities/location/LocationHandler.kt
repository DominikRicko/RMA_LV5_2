package hr.dominikricko.rma_lv5_2.utilities.location

import android.Manifest
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import hr.dominikricko.rma_lv5_2.ApplicationContext
import hr.dominikricko.rma_lv5_2.utilities.permissions.PermissionRequester
import hr.dominikricko.rma_lv5_2.utilities.permissions.State
import pub.devrel.easypermissions.EasyPermissions


class LocationHandler private constructor() : LocationListener {

    companion object {
        private lateinit var instance: LocationHandler
        const val PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION

        fun getInstance(): LocationHandler {
            if (!this::instance.isInitialized) instance = LocationHandler()
            return instance
        }

    }

    private val locationManager = ApplicationContext.context
        .getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private fun trackLocation() {

        if (EasyPermissions.hasPermissions(ApplicationContext.context, PERMISSION)) {
            startTrackingLocation()
        } else {
            PermissionRequester.requestPermissions(PERMISSION) {
                if (it[0].state == State.GRANTED)
                    trackLocation()

            }
        }
    }

    private fun stopTrackingLocation() {
        locationManager.removeUpdates(this)
    }

    private fun startTrackingLocation() {
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        val provider = locationManager.getBestProvider(criteria, true)
            ?: LocationManager.NETWORK_PROVIDER
        val minTime = 1000L
        val minDistance = 10.0F
        try {
            locationManager.requestLocationUpdates(provider, minTime, minDistance, this)
        } catch (e: SecurityException) {

        }
    }

    private fun hasSubscribers(): Boolean {
        return subscribers.size > 0
    }

    private val subscribers = arrayListOf<LocationListenerObserver>()

    fun subscribe(subscriber: LocationListenerObserver) {
        if (!subscribers.contains(subscriber)) subscribers.add((subscriber))
        if (hasSubscribers()) trackLocation()
    }

    fun unsubscribe(subscriber: LocationListenerObserver) {
        subscribers.remove(subscriber)
        if (!hasSubscribers()) stopTrackingLocation()
    }

    override fun onLocationChanged(location: Location) {

        var index = 0
        var subscriberCount = subscribers.size

        while (index < subscriberCount){

            subscribers[index].update(location)

            if (subscriberCount == subscribers.size) index++
            subscriberCount = subscribers.size
        }

    }


}