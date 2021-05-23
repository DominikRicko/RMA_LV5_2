package hr.dominikricko.rma_lv5_2.utilities.location

import android.location.Location

interface LocationListenerObserver {

    fun update(location: Location)

}