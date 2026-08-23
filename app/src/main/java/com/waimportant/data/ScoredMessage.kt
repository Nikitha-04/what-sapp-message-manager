package com.waimportant.data

data class ScoredMessage(
    val message: WhatsAppMessage,
    val isImportant: Boolean,
    val reason: String
)
