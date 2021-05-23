package hr.dominikricko.rma_lv5_2.ui.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hr.dominikricko.rma_lv5_2.utilities.Sounds
import hr.dominikricko.rma_lv5_2.utilities.location.LocationHandler
import hr.dominikricko.rma_lv5_2.utilities.location.LocationListenerObserver

class ActivityViewModel : ViewModel(), LocationListenerObserver {

    private lateinit var map: GoogleMap
    private val sounds = Sounds.getInstance()
    private val locationHandler = LocationHandler.getInstance()

    var latitude: String = "Geografska širina: 0.00000"
    var longitude: String = "Geografska dužina: 0.00000"
    var address: String = "Adresa: unknown"
    var state: String = "Država: unknown"
    var place: String = "Mjesto: unknown"

    init {
        locationHandler.subscribe(this)
    }

    fun giveMap(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMapLongClickListener { addMarker(it) }
    }

    fun setMapToNewLocation(latitudeLongitude: LatLng) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latitudeLongitude, 10f))
    }

    fun updateProperties(location: Location) {
        latitude = StringBuilder()
            .append("Geografska širina: ")
            .append(location.latitude).toString()
    }

    private fun addMarker(latLng: LatLng) {
        val markerOptions = MarkerOptions()

        markerOptions.position(latLng)

        map.addMarker(markerOptions)
        sounds.playMarkerSound()
    }

    override fun update(location: Location) {
        setMapToNewLocation(LatLng(location.latitude, location.longitude))
        updateProperties(location)
        locationHandler.unsubscribe(this)
    }
}