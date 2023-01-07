package com.waigoma.voicetranslater.setting

import java.util.*
import kotlin.collections.HashMap

class SettingData(
    var offlineMode: Boolean,
    var RecognizeLang: String,
    var TranslateLang: String,
) {
    companion object {
        val LANG_MAP = HashMap<String, String>()

        fun initialize() {
            for (lang in Locale.getAvailableLocales()) {
                LANG_MAP[lang.displayLanguage] = lang.language
            }
        }
    }
}