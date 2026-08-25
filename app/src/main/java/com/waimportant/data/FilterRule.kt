package com.waimportant.data

data class FilterRule(
    val sender: String = "",   // blank = match any sender
    val keyword: String = ""   // blank = match any text
) {
    fun matches(msg: WhatsAppMessage): Boolean {
        val senderMatch = sender.isBlank() ||
            msg.sender.contains(sender, ignoreCase = true)
        val keywordMatch = keyword.isBlank() ||
            msg.text.contains(keyword, ignoreCase = true)
        return senderMatch && keywordMatch
    }
}