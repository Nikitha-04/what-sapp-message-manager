package com.waimportant.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object FilterRepository {
    private val _rules = MutableStateFlow<List<FilterRule>>(emptyList())
    val rules: StateFlow<List<FilterRule>> = _rules.asStateFlow()

    private var rulesFile: File? = null

    fun init(filesDir: File) {
        rulesFile = File(filesDir, "filter_rules.json")
        load()
    }

    fun add(rule: FilterRule) {
        if (rule.sender.isBlank() && rule.keyword.isBlank()) return
        _rules.value = _rules.value + rule
        save()
    }

    fun remove(index: Int) {
        _rules.value = _rules.value.toMutableList().also { it.removeAt(index) }
        save()
    }

    fun isImportant(msg: WhatsAppMessage): Boolean =
        _rules.value.any { it.matches(msg) }

    private fun load() {
        try {
            val text = rulesFile?.readText() ?: return
            val arr = JSONArray(text)
            val list = mutableListOf<FilterRule>()
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(FilterRule(
                    sender = obj.optString("sender", ""),
                    keyword = obj.optString("keyword", "")
                ))
            }
            _rules.value = list
        } catch (_: Exception) {}
    }

    private fun save() {
        try {
            val arr = JSONArray()
            for (rule in _rules.value) {
                arr.put(JSONObject().apply {
                    put("sender", rule.sender)
                    put("keyword", rule.keyword)
                })
            }
            rulesFile?.writeText(arr.toString())
        } catch (_: Exception) {}
    }
}