package hr.dominikricko.rma_lv5_2.model

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import hr.dominikricko.rma_lv5_2.utilities.GeocodingHandler
import hr.dominikricko.rma_lv5_2.utilities.location.LocationHandler
import hr.dominikricko.rma_lv5_2.utilities.location.LocationListenerObserver
import java.util.*

class LocationData : LocationListenerObserver, Observable() {

    private val locationHandler = LocationHandler.getInstance()
    private val geocodingHandler = GeocodingHandler.getInstance()

    init {
        locationHandler.subscribe(this)
    }

    private var _latitude = MutableLiveData(0.0)
    val latitude: LiveData<Double> get() = _latitude

    private var _longitude = MutableLiveData(0.0)
    val longitude: LiveData<Double> get() = _longitude

    private var _address = MutableLiveData("Unknown")
    val address: LiveData<String> get() = _address

    private var _state = MutableLiveData("Unknown")
    val state: LiveData<String> get() = _state

    private var _place = MutableLiveData("Unknown")
    val place: LiveData<String> get() = _place

    override fun update(location: Location) {
        _longitude.postValue(location.longitude)
        _latitude.postValue(location.latitude)

        val resolvedAddress = geocodingHandler.resolveAddress(location.latitude, location.longitude)

        if (resolvedAddress == null) {
            _address.postValue("Unknown")
            _place.postValue("Unknown")
            _state.postValue("Unknown")
        } else {
            resolvedAddress.let {
                _address.postValue(it.getAddressLine(0))
                _place.postValue(it.subAdminArea)
                _state.postValue(it.countryName)
            }
        }

        setChanged()
        notifyObservers(LatLng(location.latitude, location.longitude))
        locationHandler.unsubscribe(this)
    }


}