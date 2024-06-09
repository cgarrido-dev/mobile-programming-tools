package com.example.claudio_garrido_20240608_continuidad_ing_informatica

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class ActualizarImagenesWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {

        val nuevasImagenes = listOf(
            Imagen("Obra 7", "https://i.pinimg.com/474x/e3/b3/72/e3b372f27a58c42e0368a29919141af1.jpg"),
            Imagen("Obra 8", "https://guidev404.github.io/gustave-dore-website/img/dore-2.jpg")
        )

        ImageRepository.addImages(nuevasImagenes)
        enviarNotificacion()

        return Result.success()
    }

    private fun enviarNotificacion() {
        val notificationId = 1
        val channelId = "galeria_notificaciones"
        val nombreCanal = "Actualizaciones de la Galería"
        val descripcionCanal = "Notificaciones de nuevas imágenes en la galería"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importancia = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, nombreCanal, importancia).apply {
                description = descripcionCanal
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Galería de Arte")
            .setContentText("Se han agregado nuevas imágenes a la galería.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(notificationId, builder.build())
        }
    }
}
