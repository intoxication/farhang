package tj.boronov.farhang.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import tj.boronov.farhang.R
import tj.boronov.farhang.SplashScreenActivity
import tj.boronov.farhang.util.FIREBASE_FIELD_NOTIFICATION_ACTION
import tj.boronov.farhang.util.FIREBASE_FIELD_NOTIFICATION_BODY
import tj.boronov.farhang.util.FIREBASE_FIELD_NOTIFICATION_ID
import tj.boronov.farhang.util.FIREBASE_FIELD_NOTIFICATION_TITLE

class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data.let {
            if (it.isNotEmpty()) {
                sendNotification(
                    it[FIREBASE_FIELD_NOTIFICATION_TITLE].toString(),
                    it[FIREBASE_FIELD_NOTIFICATION_BODY].toString(),
                    it[FIREBASE_FIELD_NOTIFICATION_ACTION],
                    it[FIREBASE_FIELD_NOTIFICATION_ID]?.toInt() ?: 0
                )
            }
        }
    }

    override fun onNewToken(token: String) {}

    private fun sendNotification(
        title: String,
        messageBody: String,
        action: String? = null,
        notificationId: Int
    ) {
        val intent = if (action == null)
            Intent(this, SplashScreenActivity::class.java)
        else
            Intent(Intent.ACTION_VIEW, Uri.parse(action))

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val notificationBuilder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setColorized(false)
                .setVibrate(longArrayOf(0, 200, 60, 200))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val attributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()

            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(0, 200, 60, 200)
            channel.setSound(defaultSoundUri, attributes)
            channel.lightColor = ContextCompat.getColor(this, R.color.colorPrimary)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
