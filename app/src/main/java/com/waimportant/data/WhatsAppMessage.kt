package com.waimportant.data

data class WhatsAppMessage(
    val sender: String,
    val text: String,
    val timestamp: Long,
    val isGroup: Boolean,
    val chatName: String,
    val isImportant: Boolean = false
)