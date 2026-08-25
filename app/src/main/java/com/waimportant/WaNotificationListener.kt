package com.waimportant

import android.app.Notification
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.waimportant.data.FilterRepository
import com.waimportant.data.MessageRepository
import com.waimportant.data.WhatsAppMessage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WaNotificationListener : NotificationListenerService() {

    companion object {
        private val WHATSAPP_PACKAGES = setOf("com.whatsapp", "com.whatsapp.w4b")
    }

    private fun writeLog(msg: String) {
        try {
            val ts = SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())
            File(filesDir, "wa_log.txt").appendText("$ts $msg\n")
        } catch (_: Exception) {}
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        FilterRepository.init(filesDir)
        Log.e("WA_CAPTURE", "onListenerConnected FIRED")
        writeLog("onListenerConnected FIRED")
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        Log.e("WA_CAPTURE", "onListenerDisconnected FIRED")
        writeLog("onListenerDisconnected FIRED")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName !in WHATSAPP_PACKAGES) return

        Log.e("WA_CAPTURE", "WhatsApp notification received from: ${sbn.packageName}")
        writeLog("onNotificationPosted from ${sbn.packageName}")

        val extras = sbn.notification.extras ?: return
        val messagesArray = extras.getParcelableArray(Notification.EXTRA_MESSAGES)

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
                val waMsg = WhatsAppMessage(sender = sender, text = text,
                    timestamp = sbn.postTime, isGroup = false, chatName = sender,
                    isImportant = FilterRepository.isImportant(
                        WhatsAppMessage(sender, text, sbn.postTime, false, sender)))
                MessageRepository.add(waMsg)
                writeLog("MSG from $sender [important=${waMsg.isImportant}]: $text")
                Log.e("WA_CAPTURE", "MSG from $sender [important=${waMsg.isImportant}]: $text")
            }
        } else {
            val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString() ?: "Unknown"
            val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""
            val waMsg = WhatsAppMessage(sender = title, text = text,
                timestamp = sbn.postTime, isGroup = false, chatName = title,
                isImportant = FilterRepository.isImportant(
                    WhatsAppMessage(title, text, sbn.postTime, false, title)))
            MessageRepository.add(waMsg)
            writeLog("MSG from $title [important=${waMsg.isImportant}]: $text")
            Log.e("WA_CAPTURE", "MSG from $title [important=${waMsg.isImportant}]: $text")
        }
    }
}