package hr.dominikricko.rma_lv5_2.utilities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.google.android.gms.maps.model.LatLng
import hr.dominikricko.rma_lv5_2.ApplicationContext
import pub.devrel.easypermissions.EasyPermissions

@SuppressLint("MissingPermission")
class Locations private constructor() : LocationListener {

    private var _location : LatLng

    val location: LatLng
    get() = _location

    init{
        locationManager = ApplicationContext.context.
            getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE
        criteria.powerRequirement = Criteria.NO_REQUIREMENT

        locationProvider = locationManager.getBestProvider(criteria, true)
            ?: LocationManager.NETWORK_PROVIDER

        if (EasyPermissions.hasPermissions(ApplicationContext.context, PERMISSION))
            locationManager.requestLocationUpdates(locationProvider,
                1000L, 10.0f, this)

        val lastLocation = locationManager.getLastKnownLocation(locationProvider)
        _location = LatLng(lastLocation?.latitude ?: 45.8150,
            lastLocation?.longitude ?: 15.9819)

    }

    companion object{
        private lateinit var instance: Locations
        const val PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
        const val CODE = 10
        private lateinit var locationManager : LocationManager
        private lateinit var locationProvider: String

        fun getInstance(): Locations{

            if(!this::instance.isInitialized) instance = Locations()
            return instance

        }

    }

    override fun onLocationChanged(location: Location) {
        _location = LatLng(location.latitude, location.longitude)
    }

}