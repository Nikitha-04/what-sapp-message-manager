package com.waimportant

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.ServiceInfo
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.waimportant.data.WhatsAppMessage

class WaNotificationListener : NotificationListenerService() {
    override fun onListenerConnected() {
        super.onListenerConnected()
        createNotificationChannel()
        val notification = Notification.Builder(this, "monitor_channel")
            .setContentTitle("Message Monitor")
            .setContentText("Message monitor active")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
            
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val type = if (Build.VERSION.SDK_INT >= 34) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            } else {
                0
            }
            startForeground(1, notification, type)
        } else {
            startForeground(1, notification)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "monitor_channel",
                "Message Monitor",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName != "com.whatsapp") return

        val extras = sbn.notification.extras ?: return
        val messagesArray = extras.getParcelableArray(Notification.EXTRA_MESSAGES)

        val parsedMessages = mutableListOf<WhatsAppMessage>()

        if (messagesArray != null) {
            val messages = Notification.MessagingStyle.Message.getMessagesFromBundleArray(messagesArray)
            for (msg in messages) {
                val text = msg.text?.toString() ?: ""
                val sender = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    msg.senderPerson?.name?.toString() ?: "Unknown"
                } else {
                    @Suppress("DEPRECATION")
                    msg.sender?.toString() ?: "Unknown"
                }
                parsedMessages.add(
                    WhatsAppMessage(
                        sender = sender,
                        text = text,
                        timestamp = sbn.postTime,
                        isGroup = false,
                        chatName = sender
                    )
                )
            }
        } else {
            val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString() ?: "Unknown"
            val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""
            parsedMessages.add(
                WhatsAppMessage(
                    sender = title,
                    text = text,
                    timestamp = sbn.postTime,
                    isGroup = false,
                    chatName = title
                )
            )
        }

        for (msg in parsedMessages) {
            Log.d("WA_CAPTURE", msg.toString())
        }
    }
}
