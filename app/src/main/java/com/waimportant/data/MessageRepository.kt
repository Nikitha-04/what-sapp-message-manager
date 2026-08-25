package com.waimportant.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object MessageRepository {
    private val _messages = MutableStateFlow<List<WhatsAppMessage>>(emptyList())
    val messages: StateFlow<List<WhatsAppMessage>> = _messages.asStateFlow()

    fun add(msg: WhatsAppMessage) {
        _messages.value = (_messages.value + msg).takeLast(100)
    }

    fun clear() {
        _messages.value = emptyList()
    }
}