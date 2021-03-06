package hr.dominikricko.rma_lv5_2.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import hr.dominikricko.rma_lv5_2.R
import hr.dominikricko.rma_lv5_2.databinding.ActivityMainBinding
import hr.dominikricko.rma_lv5_2.ui.viewmodel.ActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<ActivityViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.also { it ->
            it.mvMap.onCreate(savedInstanceState)
            it.btnTakePhoto.setOnClickListener { viewModel.storePhoto() }
            it.mvMap.getMapAsync { googleMap -> viewModel.giveMap(googleMap) }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mvMap.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mvMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mvMap.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mvMap.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mvMap.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        binding.mvMap.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mvMap.onLowMemory()
    }

}