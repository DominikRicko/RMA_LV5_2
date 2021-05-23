package hr.dominikricko.rma_lv5_2.utilities.permissions

import android.content.Intent
import hr.dominikricko.rma_lv5_2.ApplicationContext
import java.util.concurrent.ConcurrentHashMap

object PermissionRequester {
    private val callbackMap = ConcurrentHashMap<Int, (List<PermissionResult>) -> Unit>(1)
    private var requestCode = 256
        get() {
            requestCode = field--
            return if (field < 0) 255 else field
        }

    fun requestPermissions(
        vararg permissions: String,
        callback: (List<PermissionResult>) -> Unit): () -> Unit {

        val intent = Intent(ApplicationContext.context, PermissionRequestActivity::class.java)
            .putExtra(PERMISSIONS_ARGUMENT_KEY, permissions)
            .putExtra(REQUEST_CODE_ARGUMENT_KEY, requestCode)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ApplicationContext.context.startActivity(intent)
        callbackMap[requestCode] = callback
        return { callbackMap.remove(requestCode) }
    }

    internal fun onPermissionResult(responses: List<PermissionResult>, requestCode: Int) {
        callbackMap[requestCode]?.invoke(responses)
        callbackMap.remove(requestCode)
    }
}