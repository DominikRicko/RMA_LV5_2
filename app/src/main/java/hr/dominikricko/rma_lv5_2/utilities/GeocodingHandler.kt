package hr.dominikricko.rma_lv5_2.utilities

import android.location.Address
import android.location.Geocoder
import hr.dominikricko.rma_lv5_2.ApplicationContext

class GeocodingHandler private constructor() {

    companion object {
        private lateinit var instance: GeocodingHandler

        fun getInstance(): GeocodingHandler {
            if (!this::instance.isInitialized) instance = GeocodingHandler()
            return instance
        }

    }

    private val geocoder: Geocoder? =
        if (Geocoder.isPresent()) Geocoder(ApplicationContext.context)
        else null

    fun resolveAddress(latitude: Double, longitude: Double): Address? {
        if (!Geocoder.isPresent()) return null

        return geocoder?.getFromLocation(latitude, longitude, 1)?.get(0)
    }

}