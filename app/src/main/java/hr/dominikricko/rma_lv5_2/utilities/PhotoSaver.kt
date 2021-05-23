package hr.dominikricko.rma_lv5_2.utilities

import android.graphics.Bitmap
import android.os.Environment
import android.widget.Toast
import hr.dominikricko.rma_lv5_2.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object PhotoSaver {

    private lateinit var currentPhotoPath: String

    fun savePhoto(name: String, photo: Bitmap){
        try{
            val file = createImageFile(name)
            FileOutputStream(file).use { out ->
                photo.compress(Bitmap.CompressFormat.PNG, 100, out)
            }

        }
        catch(e: IOException){
            Toast.makeText(ApplicationContext.context,"Failed storing image",Toast.LENGTH_SHORT)
                .show()
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(name: String): File {

        val storageDir: File? = ApplicationContext.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${name}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

}