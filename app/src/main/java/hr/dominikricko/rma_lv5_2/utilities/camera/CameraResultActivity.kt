package hr.dominikricko.rma_lv5_2.utilities.camera

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

class CameraResultActivity : AppCompatActivity() {

    private var outerRequestCode by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            requestPhoto()
        }
    }

    private fun requestPhoto() {
        outerRequestCode = intent?.getIntExtra(CameraHandler.REQUEST_KEY, -1) ?: -1

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, outerRequestCode)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != outerRequestCode || resultCode != RESULT_OK) {
            return
        }
        finishWithResult(data?.extras?.get("data") as Bitmap)
    }

    private fun finishWithResult(photo: Bitmap? = null) {
        val requestCode = intent?.getIntExtra(CameraHandler.REQUEST_KEY, -1) ?: -1
        CameraHandler.onCameraResult(photo, requestCode)
        finish()
    }
}