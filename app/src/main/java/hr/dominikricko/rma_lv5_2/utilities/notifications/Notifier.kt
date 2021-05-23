package hr.dominikricko.rma_lv5_2.utilities.notifications

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import hr.dominikricko.rma_lv5_2.ApplicationContext
import java.io.File


object Notifier {

    fun notify(file: File) {

        val intent = Intent(ApplicationContext.context, Notification::class.java)
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(file.toUri(), "image/*");
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION +
                Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pIntent = PendingIntent.getActivity(
            ApplicationContext.context,
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val bm = BitmapFactory.decodeFile(file.path)

        val builder = NotificationCompat.Builder(ApplicationContext.context)
        val notification = builder.setContentTitle("Taken photo")
            .setContentText("Tap to view.")
            .setContentIntent(pIntent)
            .setLargeIcon(bm.asImageBitmap().asAndroidBitmap())
            .build()

        notification.flags = Notification.FLAG_AUTO_CANCEL

        val notifManager =
            ApplicationContext.context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notifManager.notify(1337, notification)

    }

}