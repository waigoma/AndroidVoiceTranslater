package com.waigoma.voicetranslater.setting

import java.util.*
import kotlin.collections.HashMap

/**
 * 設定データ
 * @property RecognizeLang 認識言語
 * @property TranslateLang 翻訳言語
 * @property offlineMode オフラインモード
 */
class SettingData(
    var RecognizeLang: String,
    var TranslateLang: String,
    var offlineMode: Boolean,
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