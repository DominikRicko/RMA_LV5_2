package hr.dominikricko.rma_lv5_2.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hr.dominikricko.rma_lv5_2.utilities.Locations
import hr.dominikricko.rma_lv5_2.utilities.Sounds
import hr.dominikricko.rma_lv5_2.utilities.permissions.Cancellable
import hr.dominikricko.rma_lv5_2.utilities.permissions.PermissionRequester
import hr.dominikricko.rma_lv5_2.utilities.permissions.State

class ActivityViewModel : ViewModel(){

    private lateinit var map : GoogleMap
    private val sounds = Sounds.getInstance()
    private val locations = Locations.getInstance()

    var latitude : String = "Geografska širina: 0.00000"
    var longitude : String = "Geografska dužina: 0.00000"
    var address : String = "Adresa: unknown"
    var state : String = "Država: unknown"
    var place : String = "Mjesto: unknown"

    var cancelRequest = requestLocationPermission()

    fun giveMap(googleMap: GoogleMap){
        map = googleMap
        map.setOnMapLongClickListener { addMarker(it) }
    }

    private fun addMarker(latLng: LatLng){
        val markerOptions = MarkerOptions()

        markerOptions.position(latLng)

        map.addMarker(markerOptions)
        sounds.playMarkerSound()
    }

    private fun setMapToCurrentLocation(){
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
            locations.getCurrentLocation(), 10f)
        map.animateCamera(cameraUpdate)
    }

    private fun requestLocationPermission() : Cancellable{

        return PermissionRequester.requestPermissions( Locations.PERMISSION){

            it.forEach {
                if(it.state == State.GRANTED && it.permission == Locations.PERMISSION)
                    setMapToCurrentLocation()

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelRequest()
    }

}