package hr.dominikricko.rma_lv5_2.utilities.camera

import android.content.Intent
import android.graphics.Bitmap
import hr.dominikricko.rma_lv5_2.ApplicationContext
import java.util.concurrent.ConcurrentHashMap

object CameraHandler {

    const val REQUEST_KEY = "NEED_PHOTO"

    private val callbackMap = ConcurrentHashMap<Int, (Bitmap?) -> Unit>(1)
    private var requestCode = 256
        get() {
            requestCode = field--
            return if (field < 0) 255 else field
        }

    fun requestPhoto(callback: (Bitmap?) -> Unit): () -> Unit {

        val intent = Intent(ApplicationContext.context, CameraResultActivity::class.java)
            .putExtra(REQUEST_KEY, requestCode)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ApplicationContext.context.startActivity(intent)
        callbackMap[requestCode] = callback
        return { callbackMap.remove(requestCode) }
    }

    internal fun onCameraResult(photo: Bitmap?, requestCode: Int) {
        callbackMap[requestCode]?.invoke(photo)
        callbackMap.remove(requestCode)
    }

}