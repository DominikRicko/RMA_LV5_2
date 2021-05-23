package hr.dominikricko.rma_lv5_2

import android.app.Application
import android.content.Context
import hr.dominikricko.rma_lv5_2.di.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ApplicationContext : Application() {

    companion object {
        lateinit var instance: ApplicationContext

        val context: Context
            get() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidContext(this@ApplicationContext)
            modules(viewModel)
        }
    }

}