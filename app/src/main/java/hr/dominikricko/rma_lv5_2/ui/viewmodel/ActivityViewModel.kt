package hr.dominikricko.rma_lv5_2.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ActivityViewModel : ViewModel(){

    private lateinit var map : GoogleMap

    var latitude : String = "Geografska širina: 0.00000"
    var longitude : String = "Geografska dužina: 0.00000"
    var address : String = "Adresa: unknown"
    var state : String = "Država: unknown"
    var place : String = "Mjesto: unknown"

    fun giveMap(googleMap: GoogleMap){
        map = googleMap
        map.setOnMapLongClickListener { addMarker(it) }
    }

    private fun addMarker(latLng: LatLng){
        val markerOptions = MarkerOptions()

        markerOptions.position(latLng)

        map.addMarker(markerOptions)
    }
}