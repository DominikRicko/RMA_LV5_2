package hr.dominikricko.rma_lv5_2.utilities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.LocationManager
import com.google.android.gms.maps.model.LatLng
import hr.dominikricko.rma_lv5_2.ApplicationContext
import pub.devrel.easypermissions.EasyPermissions

class Locations private constructor() {

    init{
        locationManager = ApplicationContext.context.
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    companion object{
        private lateinit var instance: Locations
        const val PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
        private lateinit var locationManager : LocationManager

        fun getInstance(): Locations{

            if(this::instance.isInitialized) instance = Locations()
            return instance

        }

    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() : LatLng{

        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE

        val locationProvider = locationManager.getBestProvider(criteria, true)
        if(locationProvider.isNullOrBlank()) return LatLng(0.00, 0.00)

        if (EasyPermissions.hasPermissions(ApplicationContext.context, PERMISSION)) {
            val location = locationManager.getLastKnownLocation(locationProvider)
            return LatLng(location?.latitude ?: 0.0, location?.longitude ?: 0.0)
        }

        return LatLng(0.0,0.0)

    }


}