package hr.dominikricko.rma_lv5_2.utilities.permissions

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class PermissionRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        val permissions = intent?.getStringArrayExtra(PERMISSIONS_ARGUMENT_KEY) ?: arrayOf()
        val requestCode = intent?.getIntExtra(REQUEST_CODE_ARGUMENT_KEY, -1) ?: -1
        when {
            permissions.isNotEmpty() && requestCode != -1 -> ActivityCompat.requestPermissions(
                this,
                permissions,
                requestCode
            )
            else -> finishWithResult()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val permissionResults = grantResults.zip(permissions).map { (grantResult, permission) ->
            val state = when {
                grantResult == PackageManager.PERMISSION_GRANTED -> State.GRANTED
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    permission
                ) -> State.DENIED_TEMPORARILY
                else -> State.DENIED_PERMANENTLY
            }
            PermissionResult(permission, state)
        }

        finishWithResult(permissionResults)
    }

    private fun finishWithResult(permissionResult: List<PermissionResult> = listOf()) {
        val requestCode = intent?.getIntExtra(REQUEST_CODE_ARGUMENT_KEY, -1) ?: -1
        PermissionRequester.onPermissionResult(permissionResult, requestCode)
        finish()
    }
}