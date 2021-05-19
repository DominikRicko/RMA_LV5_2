package hr.dominikricko.rma_lv5_2

import android.app.Application
import android.content.Context

class ApplicationContext: Application() {

    companion object {
        lateinit var instance: ApplicationContext

        val context: Context
            get() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}