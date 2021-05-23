package hr.dominikricko.rma_lv5_2.ui.viewmodel

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hr.dominikricko.rma_lv5_2.ApplicationContext
import hr.dominikricko.rma_lv5_2.model.LocationData
import hr.dominikricko.rma_lv5_2.utilities.PhotoSaver
import hr.dominikricko.rma_lv5_2.utilities.Sounds
import hr.dominikricko.rma_lv5_2.utilities.camera.CameraHandler
import java.lang.StringBuilder
import java.util.*


class ActivityViewModel : ViewModel(), Observer {

    private lateinit var map: GoogleMap
    private val sounds = Sounds.getInstance()
    private val locationData = LocationData()
    private val cameraHandler = CameraHandler

    val latitude = locationData.latitude
    val longitude = locationData.longitude
    val address = locationData.address
    val state = locationData.state
    val place = locationData.place

    init {
        locationData.addObserver(this)
    }

    fun giveMap(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMapLongClickListener { addMarker(it) }
    }

    private fun setMapToNewLocation(latitudeLongitude: LatLng) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latitudeLongitude, 10f))
    }

    private fun addMarker(latLng: LatLng) {
        val markerOptions = MarkerOptions()

        markerOptions.position(latLng)

        map.addMarker(markerOptions)
        sounds.playMarkerSound()
    }

    override fun update(o: Observable?, arg: Any?) {
        if(o == locationData){
            setMapToNewLocation( arg as? LatLng ?: LatLng(0.0, 0.0))
            locationData.deleteObserver(this)
        }
    }

    fun storePhoto(){
        cameraHandler.requestPhoto {
            if (it != null) {
                val filename = StringBuilder()
                    .append("Lat").append(latitude.value)
                    .append("Long").append(longitude.value)
                    .toString()

                val file = PhotoSaver.savePhoto(filename, it)

                file?.let {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.setDataAndType(file.toUri(), "image/*");
                    startActivity(ApplicationContext.context, intent, null);
                }

            }

        }
    }

}