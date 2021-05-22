package hr.dominikricko.rma_lv5_2.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hr.dominikricko.rma_lv5_2.ApplicationContext
import hr.dominikricko.rma_lv5_2.utilities.Locations
import hr.dominikricko.rma_lv5_2.utilities.Sounds
import pub.devrel.easypermissions.EasyPermissions

class ActivityViewModel : ViewModel(){

    private lateinit var map : GoogleMap
    private val sounds = Sounds.getInstance()
    private val locations = Locations.getInstance()

    var latitude : String = "Geografska širina: 0.00000"
    var longitude : String = "Geografska dužina: 0.00000"
    var address : String = "Adresa: unknown"
    var state : String = "Država: unknown"
    var place : String = "Mjesto: unknown"

    fun giveMap(googleMap: GoogleMap){
        map = googleMap
        map.setOnMapLongClickListener { addMarker(it) }

        if(EasyPermissions.hasPermissions(ApplicationContext.context,Locations.PERMISSION))
            setMapToCurrentLocation()
    }

    private fun addMarker(latLng: LatLng){
        val markerOptions = MarkerOptions()

        markerOptions.position(latLng)

        map.addMarker(markerOptions)
        sounds.playMarkerSound()
    }

    private fun updateFields(){
        val latitudeBuilder = StringBuilder()
        val longitudeBuilder = StringBuilder()
        val addressBuilder = StringBuilder()
        val stateBuilder = StringBuilder()
        val placeBuilder = StringBuilder()

        latitudeBuilder.append("Geografska širina: ")
        longitudeBuilder.append("Geografska dužina: ")
        addressBuilder.append("Adresa: ")
        stateBuilder.append("Država: ")
        placeBuilder.append("Mjesto: ")
    }

    fun setMapToCurrentLocation(){
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(locations.location, 10f)
        map.animateCamera(cameraUpdate)
    }

}